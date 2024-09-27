package com.bangkit.fixmyrideapp.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.fixmyrideapp.data.api.ApiService
import com.bangkit.fixmyrideapp.data.response.NewsData
import com.bangkit.fixmyrideapp.data.response.NewsResponse
import com.bangkit.fixmyrideapp.data.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor(
    private var apiService: ApiService,
    private var context: Context
){

    fun getAllNews(): LiveData<Result<List<NewsData>>> {
        val newsLiveData = MutableLiveData<Result<List<NewsData>>>()
        newsLiveData.value = Result.Loading

        apiService.getNews().enqueue( object : Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                if (response.isSuccessful){
                    val newsData = response.body()?.data ?: emptyList()
                    newsLiveData.value = Result.Success(newsData)
                    Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                } else {
                    newsLiveData.value = Result.Error("Failed to get news data")
                    Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                newsLiveData.value = com.bangkit.fixmyrideapp.data.utils.Result.Error("An error occurred")
            }

        })
        return newsLiveData
    }


    companion object {
        @Volatile
        private var INSTANCE: NewsRepository? = null

        fun getInstance(apiService: ApiService, context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NewsRepository(apiService, context)
            }.also { INSTANCE = it }

        private const val TAG = "NewsRepository"
    }
}