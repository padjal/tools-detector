package ru.hse.toolsdetector.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ImageScreen(
    navController: NavController,
) {
    DisplayImage()
}

@Composable
fun DisplayImage(
    imagePath: String = "https://media.istockphoto.com/id/951985126/vector/fail-ink-stamp.jpg?s=612x612&w=0&k=20&c=YIHZIUaRLJqNArnsvWWIswGIn3Q5y7FWxUsNQs-rzrQ="
) {
    AsyncImage(
        model = imagePath,
        contentDescription = null
    )

}

@Preview
@Composable
fun DisplayImagePreview() {
    DisplayImage(imagePath = "https://c8.alamy.com/comp/2F5WKKM/hammer-and-wrench-tools-cartoon-icon-realistic-hammer-tool-and-metal-wrench-key-working-kit-repair-equipment-symbols-vector-illustration-isolated-o-2F5WKKM.jpg")
}