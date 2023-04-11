package ru.hse.toolsdetector.data.datasources

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.ArrayMap
import androidx.annotation.BinderThread
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.JsonObject
import ru.hse.toolsdetector.data.models.YoloResultModel
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap

class ModelDataSource {
    val API_KEY = "yVto5AHXBNvGKUSGKMFM" // Your API Key
    val MODEL_ENDPOINT = "industrial-tools/7" // Set model endpoint (Found in Dataset URL)

    /**
     * Analyzes an image and returns a result in the form of a YoloResultModel.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    public fun analyzeImage(uri: Uri): YoloResultModel {
        // Get annotated Image
        val image = getAnnotatedImage(uri)

        // Get recognized objects
        val recognizedObjects = getRecognizedObjects(uri)

        return YoloResultModel(image, recognizedObjects)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAnnotatedImage(uri: Uri) : Bitmap? {
        val encodedFile = encodeImage(uri)

        // Construct the URL
        val uploadURL =
            "https://detect.roboflow.com/" + MODEL_ENDPOINT + "?api_key=" + API_KEY + "&name=YOUR_IMAGE.jpg" + "&format=image&labels=true";

        // Http Request
        var connection: HttpURLConnection? = null

        var image : Bitmap? = null

        try {
            // Configure connection to URL
            val url = URL(uploadURL)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
            )
            connection.setRequestProperty(
                "Content-Length",
                Integer.toString(encodedFile.toByteArray().size)
            )
            connection.setRequestProperty("Content-Language", "en-US")
            connection.useCaches = false
            connection.doOutput = true

            //Send request
            val wr = DataOutputStream(
                connection.outputStream
            )
            wr.writeBytes(encodedFile)
            wr.close()

            // Get Response
            val stream = connection.inputStream

            image = getBitmapFromInputStream(stream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }

        return image
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getRecognizedObjects(uri: Uri):Map<String, Int>{
        val encodedFile = encodeImage(uri)
        var results : Map <String, Int>

        // Construct the URL
        val uploadURL =
            "https://detect.roboflow.com/" + MODEL_ENDPOINT + "?api_key=" + API_KEY + "&name=YOUR_IMAGE.jpg";

        // Http Request
        var connection: HttpURLConnection? = null

        try {
            // Configure connection to URL
            val url = URL(uploadURL)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
            )
            connection.setRequestProperty(
                "Content-Length",
                Integer.toString(encodedFile.toByteArray().size)
            )
            connection.setRequestProperty("Content-Language", "en-US")
            connection.useCaches = false
            connection.doOutput = true

            //Send request
            val wr = DataOutputStream(
                connection.outputStream
            )
            wr.writeBytes(encodedFile)
            wr.close()

            // Get Response
            val stream = connection.inputStream

            // Convert InputStream to String
            val inputString = stream.bufferedReader().use { it.readText() }

            // Convert String to JSON using Gson library
            val gson = Gson()
            val json = gson.fromJson(inputString, JsonObject::class.java)

            results = parseJson(json)
        } catch (e: Exception) {
            e.printStackTrace()

            results = ArrayMap()
        } finally {
            connection?.disconnect()
        }

        return results
    }

    private fun parseJson(json : JsonObject) : Map<String, Int>{
        val results = HashMap<String, Int>()

        val predictions = json.get("predictions")

        for(prediction in predictions.asJsonArray){
            val predictionElement = prediction.asJsonObject

            val className= predictionElement.get("class").asString

            if(results.contains(className)){
                results[className] = results[className]!! + 1
            }else{
                results[className] = 1
            }
        }

        return results
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeImage(imageUri:Uri) : String{
        val file = File(imageUri.path)

        // Base 64 Encode
        val encodedFile: String
        val fileInputStreamReader = FileInputStream(file)
        val bytes = ByteArray(file.length().toInt())
        fileInputStreamReader.read(bytes)
        return String(Base64.getEncoder().encode(bytes), StandardCharsets.US_ASCII)
    }

    // Function to convert an InputStream to a Bitmap
    private fun getBitmapFromInputStream(inputStream: InputStream): Bitmap {
        return BitmapFactory.decodeStream(inputStream)
    }
}