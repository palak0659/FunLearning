package com.example.funlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class AboutUs : AppCompatActivity() {


        lateinit var firebaseAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_about)
            firebaseAuth = FirebaseAuth.getInstance()
        }
}
