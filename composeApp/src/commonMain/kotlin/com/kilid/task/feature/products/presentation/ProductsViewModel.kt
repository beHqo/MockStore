package com.kilid.task.feature.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.interactor.FetchProductsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProductsViewModel(private val fetchProductsUseCase: FetchProductsUseCase) : ViewModel() {
    private val refresh = MutableStateFlow(false)

    private val _selectedCategory = MutableStateFlow<ProductCategory?>(null)
    val selectedCategory: StateFlow<ProductCategory?> = _selectedCategory.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class) //flatMapLatest
    val products = selectedCategory.combine(refresh) { category, refresh -> category }
        .flatMapLatest { category -> fetchProductsUseCase(category) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    fun setCategory(category: ProductCategory?) {
        _selectedCategory.update { if (category == it) null else category }
    }

    fun refresh() {
        refresh.update { !it }
    }
}
