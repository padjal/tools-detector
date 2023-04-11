package ru.hse.toolsdetector.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.hse.toolsdetector.ui.screens.CameraScreen
import ru.hse.toolsdetector.ui.screens.ImageScreen

@Composable
fun AppNavHost(navController:NavHostController){
    NavHost(
        navController = navController,
        startDestination = Route.Camera.route
    ) {
        composable(route = Route.Camera.route) {
            CameraScreen(navController)
        }
        composable(route = Route.Image.route) {
            ImageScreen(navController)
        }
    }
}