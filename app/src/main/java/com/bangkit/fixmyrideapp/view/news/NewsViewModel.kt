package com.bangkit.fixmyrideapp.view.news

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.fixmyrideapp.data.injection.Injection
import com.bangkit.fixmyrideapp.data.repository.NewsRepository
import com.bangkit.fixmyrideapp.data.response.NewsResponse

class NewsViewModel(private val newsRepository: NewsRepository, context: Context): ViewModel() {
    fun getNews() = newsRepository.getAllNews()

    class NewsFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(Injection.provideRepository(context), context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}