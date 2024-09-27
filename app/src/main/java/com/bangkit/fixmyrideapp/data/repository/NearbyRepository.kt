package com.bangkit.fixmyrideapp.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.fixmyrideapp.data.response.NearbyItem
import com.bangkit.fixmyrideapp.data.response.NearbyRequest
import com.bangkit.fixmyrideapp.data.api.ApiService
import com.bangkit.fixmyrideapp.data.response.NearbyResponse
import com.bangkit.fixmyrideapp.data.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NearbyRepository private constructor(
    private val apiService: ApiService,
    private val context: Context
){

    fun getLocationNearby(latitude: String, longitude: String, radius: String, count: String) : LiveData<Result<List<NearbyItem>>>{
        val nearbyLiveData = MutableLiveData<Result<List<NearbyItem>>>()
        nearbyLiveData.value = Result.Loading

        val request = NearbyRequest(latitude, longitude, radius, count)
        apiService.getNearby(request).enqueue( object : Callback<NearbyResponse>{
            override fun onResponse(
                call: Call<NearbyResponse>,
                response: Response<NearbyResponse>,
            ) {
                if (response.isSuccessful){
                    val nearby = response.body()?.result ?: emptyList()
                    nearbyLiveData.value = Result.Success(nearby)
                }else{
                    nearbyLiveData.value = Result.Error("Gagal mendapatkan lokasi terdekat")
                    Log.e("RecipeFoodRepository", "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NearbyResponse>, t: Throwable) {
                Log.e("RecipeFoodRepository", "onFailure: ${t.message}")
                nearbyLiveData.value = Result.Error("Terjadi kesalahan")
            }

        })
        return nearbyLiveData
    }


    companion object{
        @Volatile
        private var INSTANCE: NearbyRepository? = null

        fun getInstance(apiService: ApiService, context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: NearbyRepository(apiService, context)
            }.also { INSTANCE = it }

        private const val TAG = "NearbyRepository"
    }
}