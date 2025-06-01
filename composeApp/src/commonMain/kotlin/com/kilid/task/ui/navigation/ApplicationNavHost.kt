package com.kilid.task.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kilid.task.feature.product_details.presentation.ProductDetailsScreen
import com.kilid.task.feature.products.presentation.ProductScreen
import com.kilid.task.ui.navigation.routes.ProductDetailsScreen
import com.kilid.task.ui.navigation.routes.ProductsScreen

@Composable
fun ApplicationNavHost(navController: NavHostController) = NavHost(navController, ProductsScreen) {
    composable<ProductsScreen> { ProductScreen { navController.navigate(ProductDetailsScreen(it)) } }
    composable<ProductDetailsScreen> { ProductDetailsScreen { navController.navigateUp() } }
}