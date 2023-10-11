package com.example.csc_mvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.csc_mvvm.ui.component.login.LoginViewModel
import com.example.csc_mvvm.usecase.UserUseCase

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(UserUseCase())

            else -> throw IllegalArgumentException("Unknown class name")
        }
    } as T
}