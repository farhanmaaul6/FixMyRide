package com.bangkit.fixmyrideapp.data.api

import com.bangkit.fixmyrideapp.data.response.PredictionRequest
import com.bangkit.fixmyrideapp.data.response.PredictionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceML {
    @POST("/predict")
    suspend fun predict(@Body request: PredictionRequest): PredictionResponse
}