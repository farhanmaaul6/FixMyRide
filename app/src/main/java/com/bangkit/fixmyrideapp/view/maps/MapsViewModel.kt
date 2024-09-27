package com.bangkit.fixmyrideapp.view.maps

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.fixmyrideapp.data.injection.Injection
import com.bangkit.fixmyrideapp.data.repository.NearbyRepository

class MapsViewModel(private val nearbyRepository: NearbyRepository) : ViewModel() {
    fun getNearbyLocation(latitude: String, longitude: String, radius: String, count: String) =
        nearbyRepository.getLocationNearby(latitude, longitude, radius, count)

    class SearchFoodRecipeFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(Injection.provideNearbyRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}