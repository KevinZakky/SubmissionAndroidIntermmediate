package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    suspend fun registerUser(name: String, email: String, password: String) {
        repository.registerUser(name, email, password)
    }
}