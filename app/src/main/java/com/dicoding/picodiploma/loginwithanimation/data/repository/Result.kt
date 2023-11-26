package com.dicoding.picodiploma.loginwithanimation.data.repository

sealed class Result<out R> private constructor() {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val data: T) : Result<Nothing>()
    object Loading : Result<Nothing>()
}