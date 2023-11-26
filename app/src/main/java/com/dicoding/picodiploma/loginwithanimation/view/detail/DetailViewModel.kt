package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.Story
import kotlinx.coroutines.launch

class DetailViewModel(private val story: StoryRepository) : ViewModel() {

    private val _detailStory = MutableLiveData<Story>()

    val detailStory: LiveData<Story> get() = _detailStory

     fun getStoryDetail(id: String, token:String){
         viewModelScope.launch {

        try{
            val detailStory = story.getDetail(id, token)
            _detailStory.value = detailStory
            Log.d("detail story", _detailStory.toString())

        }catch (e: Exception){
            Log.d("Error", e.message.toString())
        }
         }
    }
}