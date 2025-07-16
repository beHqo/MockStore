package com.kilid.task.feature.products.presentation.products

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.kilid.task.domain.model.ProductCategory
import com.kilid.task.domain.model.ProductRating
import com.kilid.task.domain.model.Result
import com.kilid.task.feature.products.domain.model.Product
import com.kilid.task.presentation.common.CenteredCircularProgressIndicator
import com.kilid.task.presentation.common.ErrorScreen
import com.kilid.task.presentation.common.RatingBar
import kilidtask.composeapp.generated.resources.Res
import kilidtask.composeapp.generated.resources.refresh
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductScreen(
    vm: ProductsViewModel = koinViewModel(),
    navigateToProductDetails: (Int) -> Unit
) {
    val productsResult = vm.products.collectAsStateWithLifecycle().value

    val selectedCategory by vm.selectedCategory.collectAsStateWithLifecycle()

    when (productsResult) {
        is Result.Loading -> CenteredCircularProgressIndicator()

        is Result.Error -> ErrorScreen(
            message = productsResult.message,
            onRefresh = vm::refresh,
            refreshButtonText = stringResource(Res.string.refresh)
        )

        is Result.Success -> {
            val categories = ProductCategory.entries.toList()
            val lazyListState = rememberLazyListState()

            LaunchedEffect(selectedCategory) {
                if (selectedCategory == null) lazyListState.animateScrollToItem(0)
            }

            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
                        .padding(start = 16.dp, top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        FilterChip(
                            selected = (selectedCategory == category),
                            onClick = { vm.setCategory(category) },
                            label = {
                                Text(text = category.rawName.replaceFirstChar { it.uppercase() })
                            })
                    }
                }

                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productsResult.data, key = { it.id }) { product ->
                        ProductCard(product = product) { navigateToProductDetails(product.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCard(product: Product, modifier: Modifier = Modifier, onClick: () -> Unit) =
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth().padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(end = 16.dp).size(112.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .align(Alignment.CenterVertically)
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                RatingBar(
                    rating = product.rating.rate,
                    count = product.rating.count,
                    modifier = Modifier.offset(y = (-4).dp)
                )

                Text(
                    text = product.category.rawName,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

@Preview
@Composable
private fun ProductCardPreview() {
    val sampleProduct = Product(
        id = 1,
        title = "Wireless Headphones",
        price = 149.99,
        description = "High-quality over-ear wireless headphones with noise cancellation.",
        category = ProductCategory.ELECTRONICS,
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        rating = ProductRating(rate = 4.2, count = 2567)
    )

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProductCard(
                product = sampleProduct, onClick = { /* no-op for preview */ })
        }
    }
}
