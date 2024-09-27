package com.bangkit.fixmyrideapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/"
        fun getClient(): ApiServiceMaps {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiServiceMaps::class.java)
        }
    }
}