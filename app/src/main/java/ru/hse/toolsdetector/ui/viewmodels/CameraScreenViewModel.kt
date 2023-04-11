package ru.hse.toolsdetector.ui.viewmodels

import android.net.Uri
import android.util.Log
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.ViewModel
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService

class CameraScreenViewModel() : ViewModel() {
    lateinit var outputDir : File
    lateinit var cameraExecutor: ExecutorService
    lateinit var photoUri: Uri
    lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        //RecognizeObjects(uri)

        photoUri = uri
        // navigate to screen
    }
}