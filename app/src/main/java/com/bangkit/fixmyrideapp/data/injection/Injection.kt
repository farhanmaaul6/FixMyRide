package com.bangkit.fixmyrideapp.data.injection

import android.content.Context
import com.bangkit.fixmyrideapp.data.api.ApiConfig
import com.bangkit.fixmyrideapp.data.repository.NearbyRepository
import com.bangkit.fixmyrideapp.data.repository.NewsRepository
import com.bangkit.fixmyrideapp.data.repository.PredictionRepository
import com.bangkit.fixmyrideapp.data.repository.ProfileRepository

object Injection {

    fun provideRepository(context: Context): NewsRepository{
        val apiService = ApiConfig.getApiService()
        return NewsRepository.getInstance(apiService, context)
    }

    fun providePredictionRepository(context: Context): PredictionRepository {
        val apiService = ApiConfig.getApiServiceML()
        return PredictionRepository.getInstance(apiService, context)
    }

    fun provideNearbyRepository(context: Context): NearbyRepository {
        val apiService = ApiConfig.getApiService()
        return NearbyRepository.getInstance(apiService, context)
    }

    fun provideProfileUser(context: Context): ProfileRepository {
        val apiService = ApiConfig.getApiService()
        return ProfileRepository.getInstance(apiService, context)
    }
}