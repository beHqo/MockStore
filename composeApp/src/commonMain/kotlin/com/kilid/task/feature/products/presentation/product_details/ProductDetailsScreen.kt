package com.kilid.task.feature.products.presentation.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import kilidtask.composeapp.generated.resources.round_arrow_back_24
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class) //TopAppBar
@Composable
fun ProductDetailsScreen(
    vm: ProductDetailsViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) = Scaffold(
    topBar = {
        TopAppBar(title = { Text("ProductDetails Details") }, navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = vectorResource(Res.drawable.round_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        })
    }, modifier = modifier
) { padding ->
    val productResult = vm.productDetails.collectAsStateWithLifecycle().value

    when (productResult) {
        is Result.Loading -> CenteredCircularProgressIndicator()

        is Result.Error -> ErrorScreen(
            message = productResult.message,
            onRefresh = vm::refresh,
            refreshButtonText = stringResource(Res.string.refresh)
        )

        is Result.Success -> ProductDetailsItem(Modifier.padding(padding), productResult.data)
    }
}

@Composable
private fun ProductDetailsItem(
    modifier: Modifier = Modifier, productDetails: Product
) = Column(
    modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
) {
    AsyncImage(
        model = productDetails.image,
        contentDescription = productDetails.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(280.dp)
    )

    Column(
        modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = productDetails.title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "$${productDetails.price}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = productDetails.category.rawName.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        RatingBar(rating = productDetails.rating.rate, count = productDetails.rating.count)

        Text(text = productDetails.description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
private fun ProductDetailsItemPreview() {
    val productDetails = Product(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price = 109.95,
        description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve.",
        category = ProductCategory.MEN_CLOTHING,
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        rating = ProductRating(rate = 3.9, count = 120)
    )

    ProductDetailsItem(productDetails = productDetails)
}