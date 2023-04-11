package ru.hse.toolsdetector.ui

sealed class Route (val route:String) {
    object Camera:Route("home_screen")
    object Image:Route("image_screen")
}