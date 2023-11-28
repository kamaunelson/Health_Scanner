package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var otp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val loginButton = findViewById<Button>(R.id.loginButton)
        val signButton = findViewById<Button>(R.id.signButton)
        val otpButton = findViewById<Button>(R.id.otpButton)
        val switch = findViewById<Switch>(R.id.switch1)

        loginButton.setOnClickListener {
            // TODO: Implement login logic
            performLogin()
        }
        signButton.setOnClickListener {
            // TODO: Implement login logic
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        otpButton.setOnClickListener {
            // TODO: Implement login logic
            val otpInput = findViewById<EditText>(R.id.otpEditText)

            if (verifyOtp(otpInput.text.toString())) {
                // The OTP is valid, navigate the user to the landing page
                val intent = Intent(this, LandingMenu::class.java)
                startActivity(intent)
                Toast.makeText(
                    baseContext,
                    "Correct OTP.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // The OTP is invalid, display an error message to the user
                Toast.makeText(this, "Invalid OTP.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performLogin() {
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        //null check on inputs
        if (usernameEditText.text.isEmpty() || passwordEditText.text.isEmpty()) {
            Toast.makeText(this, "Kindly fill all the spaces provided", Toast.LENGTH_SHORT).show()
            return
        }

        val emailinput = usernameEditText.text.toString()
        val passinput = passwordEditText.text.toString()

        auth.signInWithEmailAndPassword(emailinput, passinput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Get the user's email address from the edit text
                    val emailInput = usernameEditText.text.toString()
                    // Generate a random OTP
                    val otp = (1000..9999).random()
                    this.otp = otp.toString()
                    Toast.makeText(
                        baseContext,
                        "Successful login.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // TODO: Send email notification
                    GlobalScope.launch {
                        val emailSender = EmailSender()
                        emailSender.sendEmail(emailInput, otp)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
            .addOnFailureListener(this) {
                // Handle other registration failures
                Toast.makeText(this, "Error occurred. ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
    fun verifyOtp(otp: String): Boolean {
        return this.otp == otp
    }

}