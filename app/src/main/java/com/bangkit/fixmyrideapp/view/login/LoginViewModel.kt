package com.bangkit.fixmyrideapp.view.login

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.fixmyrideapp.data.api.ApiConfig
import com.bangkit.fixmyrideapp.data.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService().postLogin(email, password)
            apiService.enqueue( object : Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _loginResult.value = response.body()
                        _message.value = "${response.body()?.message}"
                    }else{
                        val loginFailed = Gson().fromJson(
                            response.errorBody()?.charStream(), LoginResponse::class.java
                        )
                        _loginResult.value = loginFailed
                        _message.value = loginFailed.message
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("login", "login: " + t.message)
                }

            })
        }
    }
}