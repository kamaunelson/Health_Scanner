package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SymptomInput : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom_input)

        val submitButton = findViewById<Button>(R.id.Submit)

        // Assuming you have Spinners with IDs "symptom1" to "symptom8" in your XML layout
        val spinnerIds = Array(8) { resources.getIdentifier("symptom${it + 1}", "id", packageName) }

        // Create and set adapters for each Spinner
        val symptomOptions = arrayOf(
            arrayOf("itching","skin_rash","vomiting","sunken_eyes","chills","fatigue", "yellowish_skin","joint_pain","continuous_sneezing","burning_micturition","bladder_discomfort"),
            arrayOf("skin_rash","nodal_skin_eruptions","sunken_eyes","dehydration","vomiting","high_fever","fatigue","chills","joint_pain","lethargy","yellowish_skin","nausea","abdominal_pain","cough","bladder_discomfort","foul_smell_of urine"),
            arrayOf("nodal_skin_eruptions","dischromic _patches","dehydration","diarrhoea","high_fever","sweating","fatigue","lethargy","joint_pain","vomiting","yellowish_skin","nausea","loss_of_appetite","abdominal_pain","swelling_of_stomach",
                "weight_loss","cough","foul_smell_of urine","continuous_feel_of_urine"),
            arrayOf("","dischromic _patches","diarrhoea","sweating","headache","lethargy","high_fever","vomiting","fatigue","yellowish_skin","dark_urine","loss_of_appetite","yellowing_of_eyes","swelling_of_stomach","distention_of_abdomen",
                "weight_loss","cough","breathlessness","continuous_feel_of_urine"),
            arrayOf("","headache","nausea","high_fever","fatigue","dark_urine","loss_of_appetite","family_history","yellowing_of_eyes","yellowish_skin","distention_of_abdomen","history_of_alcohol_consumption","cough","breathlessness","sweating"),
            arrayOf("","nausea","diarrhoea","headache","loss_of_appetite","high_fever","constipation","abdominal_pain","family_history","dark_urine","history_of_alcohol_consumption","fluid_overload","breathlessness","swelled_lymph_nodes","sweating","malaise"),
            arrayOf("","muscle_pain","diarrhoea","loss_of_appetite","mild_fever","headache","nausea","abdominal_pain","constipation","yellow_urine","fluid_overload","breathlessness","sweating","swelled_lymph_nodes","malaise","phlegm"),
            arrayOf("","muscle_pain","mild_fever","swelled_lymph_nodes","nausea","loss_of_appetite","diarrhoea","abdominal_pain","yellow_urine","yellowing_of_eyes","sweating","malaise","phlegm","chest_pain")
        )

        for (i in 0 until 8) {
            val spinner = findViewById<Spinner>(spinnerIds[i])
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, symptomOptions[i])
            spinner.adapter = adapter
        }

        submitButton.setOnClickListener {
            // Get selected symptom options from Spinners
            val selectedSymptoms = mutableListOf<String>()
            for (i in 0 until 8) {
                val spinner = findViewById<Spinner>(spinnerIds[i])
                val selectedOption = spinner.selectedItem.toString()
                selectedSymptoms.add(selectedOption)
            }

            // Create a JSON string with the selected symptoms
            val symptomDataJson = """
            {
                "symptoms": ["${selectedSymptoms[0]}", "${selectedSymptoms[1]}", "${selectedSymptoms[2]}", "${selectedSymptoms[3]}",
                             "${selectedSymptoms[4]}", "${selectedSymptoms[5]}", "${selectedSymptoms[6]}", "${selectedSymptoms[7]}"]
            }
            """

            // Convert the JSON string to a SymptomData object
            val symptomData = Gson().fromJson(symptomDataJson, SymptomData::class.java)

            // Define the base URL of your Flask API (use your machine's IP address)
            val baseUrl = "https://35fb-196-207-180-110.ngrok-free.app/" // Replace with your machine's IP address

            // Create a Retrofit instance
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Create an instance of your API service
            val apiService = retrofit.create(ApiService::class.java)

            // Make the POST request to send data only
            val call = apiService.sendSymptoms(symptomData)

            // Execute the request asynchronously
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Handle the successful response here
                        // No response is expected
                        Log.d("API", "Data sent successfully")

                        // Display a success message as a Toast
                        val toastMessage = "Symptoms sent successfully"
                        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
                        //take me to next activity after successful sending data
                        val intent = Intent(applicationContext, ResultDiagnosis::class.java)
                        startActivity(intent)
                    } else {
                        // Handle the error response here
                        // Display an error message or perform error handling
                        Log.e("API", "Data sending failed")
                        Toast.makeText(applicationContext, "Failed to send symptoms", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Handle network or other failures here
                    // Display an error message or perform error handling
                    Log.e("API", "API call failed: ${t.message}")
                    Toast.makeText(applicationContext, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
