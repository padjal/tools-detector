package ru.hse.toolsdetector.data.repositories

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import ru.hse.toolsdetector.data.datasources.ModelDataSource
import ru.hse.toolsdetector.data.models.YoloResultModel

class ModelRepository(
    val modelDataSource: ModelDataSource = ModelDataSource()
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun analyzeImage(uri: Uri): YoloResultModel {
        return modelDataSource.analyzeImage(uri)
    }
}