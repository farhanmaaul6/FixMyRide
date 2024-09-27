package com.bangkit.fixmyrideapp.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.fixmyrideapp.data.api.ApiServiceML
import com.bangkit.fixmyrideapp.data.response.PredictionRequest
import com.bangkit.fixmyrideapp.data.response.PredictionResponse
import com.bangkit.fixmyrideapp.data.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictionRepository private constructor(
    private val apiServiceML: ApiServiceML,
    private val context: Context,
){
    suspend fun prediction(request: PredictionRequest): Result<PredictionResponse>{
        return try {
            val response = apiServiceML.predict(request)
            Result.Success(response)
        } catch (e: Exception) {
            Log.e("PredictionError", "Failed to get prediction", e)
            Result.Error("Failed to get prediction: ${e.javaClass.simpleName} - ${e.message}")
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: PredictionRepository? = null

        fun getInstance(apiServiceML: ApiServiceML, context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: PredictionRepository(apiServiceML, context)
            }.also { INSTANCE = it }

        private const val TAG = "PredictionRepository"
    }
}