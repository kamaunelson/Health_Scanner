package com.example.healthscanner

//import android.telecom.Call
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    //@Headers("Content-Type: application/json")
    @POST("/predict")  // Replace with your actual API endpoint
    fun predictDisease(@Body SymptomData: SymptomData): Call<PredictionResponse>    //fun predictDisease(@Body SymptomData: SymptomData): Call<ResponseBody>
    @POST("/predict")
    fun sendSymptoms(@Body SymptomData: SymptomData): Call<Void>
    @GET("/results")
    fun getResult(): Call<PredictionResponse>
    // GET request for retrieving disease information
    //@GET("/predict")
    //fun getDiseaseInformation(): Call<DiseaseResponse>
}