package com.example.csc_mvvm.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.csc_mvvm.app.Res
import com.example.csc_mvvm.data.error.mapper.ErrorMapper
import com.example.csc_mvvm.usecase.errors.ErrorManager

abstract class BaseViewModel : ViewModel() {
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData
    fun showError(errorCode: Int) {
        _errorLiveData.value = ErrorManager(ErrorMapper(Res.context)).getError(errorCode).description
    }
}
