package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.repository.Result
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseStory

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStoriesWithLocation(): LiveData<Result<ResponseStory>> {
        return repository.getStoriesWithLocation()
    }
}