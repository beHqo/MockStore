package com.kilid.task.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kilid.task.feature.products.presentation.product_details.ProductDetailsScreen
import com.kilid.task.feature.products.presentation.products.ProductScreen
import com.kilid.task.presentation.navigation.routes.ProductDetailsScreen
import com.kilid.task.presentation.navigation.routes.ProductsScreen

@Composable
fun ApplicationNavHost(navController: NavHostController) = NavHost(navController, ProductsScreen) {
    composable<ProductsScreen> { ProductScreen { navController.navigate(ProductDetailsScreen(it)) } }
    composable<ProductDetailsScreen> { ProductDetailsScreen { navController.navigateUp() } }
}