package com.bangkit.fixmyrideapp.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.fixmyrideapp.data.api.ApiConfig
import com.bangkit.fixmyrideapp.data.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _regisResult = MutableLiveData<String>()
    val regisResult: LiveData<String> = _regisResult

    fun registerUser(name: String, email: String, password: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService().postRegister(name, email, password)
        apiService.enqueue( object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _regisResult.value = response.body()?.message
                }else{
                    val registerFailed = Gson().fromJson(
                        response.errorBody()?.charStream(), RegisterResponse::class.java
                    )
                    _regisResult.value = registerFailed.message
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: " + t.message)
            }

        })
    }
    companion object{
        private const val TAG = "RegisterViewModel"
    }
}