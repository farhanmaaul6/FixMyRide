package com.bangkit.fixmyrideapp.view.recomendation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bangkit.fixmyrideapp.data.api.ApiConfig
import com.bangkit.fixmyrideapp.data.injection.Injection
import com.bangkit.fixmyrideapp.data.repository.PredictionRepository
import com.bangkit.fixmyrideapp.data.response.PredictionRequest
import com.bangkit.fixmyrideapp.data.response.PredictionResponse
import com.bangkit.fixmyrideapp.data.utils.Result
import com.bangkit.fixmyrideapp.view.news.NewsViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecomendationViewModel(private val predictionRepository: PredictionRepository) : ViewModel(){

    private val _predictionResult = MutableLiveData<Result<PredictionResponse>>()
    val predictionResult: LiveData<Result<PredictionResponse>> get() = _predictionResult

    fun predict(request: PredictionRequest) {
        viewModelScope.launch {
            _predictionResult.value = Result.Loading
            try {
                val result = predictionRepository.prediction(request)
                _predictionResult.value = result
            } catch (e: Exception) {
                _predictionResult.value = Result.Error("An error occurred: ${e.message}")
            }
        }
    }
    class RecomendationFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecomendationViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return RecomendationViewModel(Injection.providePredictionRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}