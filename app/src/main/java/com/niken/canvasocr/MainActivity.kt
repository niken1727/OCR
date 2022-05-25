package com.niken.canvasocr

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.niken.canvasocr.customview.CanvasFormInput
import java.util.*


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var canvasFormLayout: CanvasFormInput
    private lateinit var outputView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        outputView = findViewById(R.id.output)
        canvasFormLayout = findViewById(R.id.canvas_form_layout)
        canvasFormLayout.setLabel("Image")
        canvasFormLayout.setListener {
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromBitmap(it, 0)
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    Log.d(TAG, "onCreate: ${visionText.text}")
                    if(!visionText.text.isNullOrEmpty()) {
                        outputView.text = "Extracted Text: ${visionText.text}"
                    }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Error: ${e}")
                }

            //firebase needs billing
//            val options = FirebaseVisionCloudTextRecognizerOptions.Builder()
//            .setLanguageHints(Arrays.asList("en", "hi"))
//            .build()
//        val textRecognizer = FirebaseVision.getInstance()
//            .getCloudTextRecognizer(options)
//            val image = FirebaseVisionImage.fromBitmap(it)
//            textRecognizer.processImage(image)
//                .addOnSuccessListener {
//                    Log.d(TAG, "onCreate: ${it}")
//                }
//                .addOnFailureListener {
//                    Log.d(TAG, "Error: ${it}")
//                }
        }
    }
}