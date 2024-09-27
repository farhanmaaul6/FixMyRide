package com.bangkit.fixmyrideapp.view.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bangkit.fixmyrideapp.data.api.ApiConfig
import com.bangkit.fixmyrideapp.data.api.ApiService
import com.bangkit.fixmyrideapp.data.injection.Injection
import com.bangkit.fixmyrideapp.data.repository.ProfileRepository
import com.bangkit.fixmyrideapp.data.response.Email
import com.bangkit.fixmyrideapp.data.response.UserDataItem
import com.bangkit.fixmyrideapp.data.utils.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository): ViewModel() {

    fun getDataDetail(email: String) = profileRepository.getUserData(email)

    class SearchFoodRecipeFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(Injection.provideProfileUser(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
