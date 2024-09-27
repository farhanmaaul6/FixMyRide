package com.bangkit.fixmyrideapp.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.fixmyrideapp.data.api.ApiService
import com.bangkit.fixmyrideapp.data.response.DetailUserResponse
import com.bangkit.fixmyrideapp.data.response.UserDataItem
import com.bangkit.fixmyrideapp.data.utils.Result
import com.bangkit.fixmyrideapp.data.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository(
    private val apiService: ApiService,
    private val context: Context,
){
    fun getUserData(email: String): LiveData<Result<UserDataItem>>{
        val dataUserLiveData =
            MutableLiveData<Result<UserDataItem>>()
        val client = apiService.getDetailUser("${SessionManager(context).getToken().token}", "${SessionManager(context).getEmail().email}")

        client.enqueue( object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                if (response.isSuccessful){
                    val dataUser = response.body()?.data
                    if (dataUser != null){
                        dataUserLiveData.value = Result.Success(dataUser)
                    }
                } else {
                    dataUserLiveData.value = Result.Error("Gagal Mendapatkan data User")
                    Log.e("profile", "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e("profile", "onFailure: ${t.message}")
                dataUserLiveData.value = Result.Error("Terjadi Kesalahan")
            }

        })
        return dataUserLiveData
    }

    companion object {
        @Volatile
        private var INSTANCE: ProfileRepository? = null

        fun getInstance(apiService: ApiService, context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ProfileRepository(apiService, context)
            }.also { INSTANCE = it }

        private const val TAG = "ProfileRepository"
    }
}