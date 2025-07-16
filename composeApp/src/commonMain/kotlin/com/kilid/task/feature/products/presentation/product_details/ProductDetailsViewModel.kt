package com.kilid.task.feature.products.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.interactor.FetchProductDetailsUseCase
import com.kilid.task.feature.products.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val fetchProductDetailsUseCase: FetchProductDetailsUseCase
) : ViewModel() {
    val productId: Int = savedStateHandle["productId"] ?: -1

    private val _productDetails = MutableStateFlow<Result<Product>>(Result.Loading)
    val productDetails = _productDetails.asStateFlow().onStart { fetchSafely() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    fun refresh() {
        _productDetails.update { Result.Loading }

        fetchSafely()
    }

    private fun fetchSafely() {
        if (productId == -1) _productDetails.update { Result.Error("Invalid product id") }
        else viewModelScope.launch {
            _productDetails.update { fetchProductDetailsUseCase(productId) }
        }
    }
}