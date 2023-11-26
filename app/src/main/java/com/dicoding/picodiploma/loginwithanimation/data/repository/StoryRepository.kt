package com.dicoding.picodiploma.loginwithanimation.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.paging3.StoryPagingSource
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseStory
import com.dicoding.picodiploma.loginwithanimation.data.response.Story
import retrofit2.HttpException

class StoryRepository(private val apiService: ApiService) {

    fun getStoriesPaging(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

//    suspend fun getStories(token: String, onSuccess: (List<ListStoryItem>) -> Unit, onError: (String) -> Unit) {
//        try {
//            val response = ApiConfig.getApiService(token).getStories()
//            if (response.error) {
//                onError(response.message)
//            } else {
//                onSuccess(response.listStory)
//            }
//        } catch (e: Exception) {
//            onError(e.message ?: "Unknown error")
//        }
//    }
    suspend fun getDetail(id: String, token: String): Story{
        val response = ApiConfig.getApiService(token).getDetailStories(id)
        if (response.error) {
            throw Exception(response.message)
        }
        return response.story
    }

    fun getStoriesWithLocation() : LiveData<Result<ResponseStory>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.getStoriesFromLocation(1)
            emit(Result.Success(result))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }
    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(apiService: ApiService): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService).also { instance = it }
            }
    }
}