package com.example.csc_mvvm.usecase.errors

import com.example.csc_mvvm.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
