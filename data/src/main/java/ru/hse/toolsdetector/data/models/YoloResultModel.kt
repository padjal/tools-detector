package ru.hse.toolsdetector.data.models

import android.graphics.Bitmap

data class YoloResultModel(
    val annotatedImage: Bitmap?,
    val recognizedObjects: Map<String, Int>
) {
}