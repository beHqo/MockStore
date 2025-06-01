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

    /**
     * A private state flow that toggles between true/false whenever `refresh()` is called.
     *
     * We only care about the emission itself, not its boolean value. Emitting a new value here
     * (even if it toggles from `false` → `true` or `true` → `false`) will trigger the combined flow
     * to re-emit the same `category`, which in turn causes `flatMapLatest` to restart
     * `fetchProductsUseCase(category)`. This is how we force a reload on demand.
     */
    private val refresh = MutableStateFlow(false)

    private val _selectedCategory = MutableStateFlow<ProductCategory?>(null)
    val selectedCategory: StateFlow<ProductCategory?> = _selectedCategory.asStateFlow()

    /**
     * A StateFlow of `Result<List<Product>>` that the UI collects.
     *
     * Implementation details:
     * - We combine `selectedCategory` and `refresh`, the lambda
     *   returns only the current category; `_` (the refresh value) is discarded.
     * - Any time `selectedCategory` changes *or* `refresh` emits a new boolean, `combine` emits the current `category`.
     * - We then call `flatMapLatest { category -> fetchProductsUseCase(category) }`, so:
     *     • The first time this is collected, `combine` emits `(null)` → `flatMapLatest` calls `fetchProductsUseCase(null)`.
     *     • When `setCategory(...)` toggles `_selectedCategory`, `combine` emits the new category → `flatMapLatest`
     *       cancels any in-flight flow and starts `fetchProductsUseCase(newCategory)`.
     *     • When `refresh()` flips `refresh` from `false` → `true`, `combine` re-emits `(currentCategory)`, so
     *       `flatMapLatest` restarts `fetchProductsUseCase(currentCategory)` even though the category did not change.
     * - Finally, we wrap the result in `stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)`,
     *   which converts the cold flow into a hot [StateFlow], scoped to this ViewModel’s coroutine scope, with:
     *     • An initial value of `Result.Loading`.
     *     • A “started” policy of `WhileSubscribed(5000)` so that it remains active for 5 seconds after the last collector
     *       is gone before stopping. A hacky but common way of surviving configuration changes in android.
     */
    @OptIn(ExperimentalCoroutinesApi::class) //flatMapLatest
    val products = selectedCategory.combine(refresh) { category, _ -> category }
        .flatMapLatest { category -> fetchProductsUseCase(category) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    fun setCategory(category: ProductCategory?) {
        _selectedCategory.update { if (category == it) null else category }
    }

    fun refresh() {
        refresh.update { !it }
    }
}
