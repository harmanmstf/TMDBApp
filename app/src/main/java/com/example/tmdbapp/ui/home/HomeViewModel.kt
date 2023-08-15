package com.example.tmdbapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbapp.model.MovieItem
import com.example.tmdbapp.network.ApiClient
import com.example.tmdbapp.util.Constants
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val movieList: MutableLiveData<List<MovieItem?>?> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage: MutableLiveData<String?> = MutableLiveData()

    fun getMovieList() {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getMovieList(token = Constants.BEARER_TOKEN)

                if (response.isSuccessful) {
                    movieList.postValue(response.body()?.movieItems)

                } else {

                    if (response.message().isNullOrEmpty()){
                        errorMessage.value = "An unknown error occurred"
                    } else {
                        errorMessage.value = response.message()
                    }

                }
            } catch (e: Exception) {
                errorMessage.value = e.message

            } finally {
                isLoading.value = false
            }
        }
    }
}