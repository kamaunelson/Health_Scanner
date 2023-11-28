package com.example.healthscanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResultDiagnosis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_diagnosis)

        val resultButton = findViewById<Button>(R.id.result)
        val nextButton = findViewById<Button>(R.id.next)
        val backButton = findViewById<Button>(R.id.back)
        val resultTextView = findViewById<TextView>(R.id.textView4)

        backButton.setOnClickListener {
            val intent = Intent(this, SymptomInput::class.java)
            startActivity(intent)
        }
        nextButton.setOnClickListener {
            val intent = Intent(this, ShareDiagnosis::class.java)
            startActivity(intent)
        }

        resultButton.setOnClickListener {
            // Define the base URL of your Flask API (use your machine's IP address)
            val baseUrl = "https://35fb-196-207-180-110.ngrok-free.app/" // Replace with your API URL

            // Create a Retrofit instance
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Create an instance of your API service
            val apiService = retrofit.create(ApiService::class.java)

            // Make the GET request to fetch the prediction result
            val call = apiService.getResult()

            call.enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                    if (response.isSuccessful) {
                        // Handle the successful response here
                        val prediction = response.body()?.prediction

                        // Display the prediction result on the user interface
                        if (prediction != null) {
                            resultTextView.text = "Prediction: $prediction"
                        } else {
                            resultTextView.text = "No prediction available"
                        }
                    } else {
                        // Handle the error response here
                        Toast.makeText(applicationContext, "Failed to fetch prediction result", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    // Handle network or other failures here
                    Toast.makeText(applicationContext, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}