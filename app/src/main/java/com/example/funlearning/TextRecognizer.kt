package com.example.funlearning

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.lang.Exception
import com.example.funlearning.TextRecognizer
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import java.util.jar.Manifest

class TextRecognizer : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var tvResult:TextView
    lateinit var btnChoosePic:Button
    var intentActivityResultLauncher:ActivityResultLauncher<Intent>?=null
    lateinit var inputImage: InputImage
    private val STORAGE_PERMISSION_CODE = 113
    var textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.text_recognizer)
        firebaseAuth = FirebaseAuth.getInstance()
        tvResult= findViewById(R.id.tvResult)
        btnChoosePic = findViewById(R.id.btnChoosePic)


        intentActivityResultLauncher =registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                val data=it.data
                val imageUri=data?.data

                convertImageToText(imageUri)
            }

        )
        btnChoosePic.setOnClickListener{
            val chooseIntent = Intent()
            chooseIntent.type = "image/*"
            chooseIntent.action=Intent.ACTION_GET_CONTENT
            intentActivityResultLauncher?.launch(chooseIntent)
        }
    }
    private fun convertImageToText(imageUri: Uri?) {
        try{
           inputImage = InputImage.fromFilePath(applicationContext,imageUri)

            val result: Task<Text> = textRecognizer.process(inputImage)
                .addOnSuccessListener {
                    tvResult.text =it.text
                }.addOnFailureListener{
                    tvResult.text="Error : ${it.message}"
                }


        }catch (e:Exception){

        }

    }
    override fun onResume() {
      super.onResume()
        checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE)
    }
    private fun checkPermission(permission:String,requestCode:Int){
        if(ContextCompat.checkSelfPermission(this@TextRecognizer,permission)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this@TextRecognizer, arrayOf(permission),requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(
                    this@TextRecognizer,
                    "Storage Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                    this@TextRecognizer,
                    "Storage Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
