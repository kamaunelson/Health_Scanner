package com.example.healthscanner

//import SymptomInput
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LandingMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_menu)

        val myProfile = findViewById<ImageButton>(R.id.myProfile)
        val mySymptom = findViewById<ImageButton>(R.id.symptomInput)
        val mysetting = findViewById<ImageButton>(R.id.setting)
        val mydiagnosis = findViewById<ImageButton>(R.id.diagnosisResult)

        // Check if the user is logged in
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser == null) {
            // The user is not logged in, so redirect to the login page
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        myProfile.setOnClickListener {
            // TODO: Implement login logic
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }

        mySymptom.setOnClickListener {
            // TODO: Implement login logic
            val intent = Intent(this, SymptomInput::class.java)
            startActivity(intent)
        }
        mysetting.setOnClickListener {
            // TODO: Implement login logic
            firebaseAuth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            Toast.makeText(
                baseContext,
                "Successfully logged out.",
                Toast.LENGTH_SHORT
            ).show()
        }

        mydiagnosis.setOnClickListener {
            // TODO: Implement login logic
            val intent = Intent(this, ResultDiagnosis::class.java)
            startActivity(intent)
        }

    }
}