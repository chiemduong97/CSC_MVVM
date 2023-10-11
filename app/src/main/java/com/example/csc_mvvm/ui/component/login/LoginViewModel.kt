package com.example.csc_mvvm.ui.component.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.client.models.profile.DataProfileResponse
import com.example.client.models.profile.ProfileResponse
import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.app.Res
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.error.mapper.ErrorMapper
import com.example.csc_mvvm.ui.base.BaseViewModel
import com.example.csc_mvvm.usecase.UserUseCase
import com.example.csc_mvvm.usecase.errors.ErrorManager
import kotlinx.coroutines.launch

class LoginViewModel(private val userUseCase: UserUseCase) : BaseViewModel() {

    private val preferences by lazy { Preferences.newInstance() }

    private val _loginEmailLiveData = MutableLiveData<Resource<Any>>()
    val loginEmailLiveData: LiveData<Resource<Any>> get() = _loginEmailLiveData
    private val _loginPasswordLiveData = MutableLiveData<Resource<DataProfileResponse>>()
    val loginPasswordLiveData: LiveData<Resource<DataProfileResponse>> get() = _loginPasswordLiveData
    private val _getUserLiveData = MutableLiveData<Resource<ProfileResponse>>()
    val getUserLiveData: LiveData<Resource<ProfileResponse>> = _getUserLiveData
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    init {
        errorManager = ErrorManager(ErrorMapper(Res.context))
    }

    fun checkEmail(email: String) {
        viewModelScope.launch {
            _loginEmailLiveData.value = Resource.Loading()
            userUseCase.checkEmail(email).collect {
                _loginEmailLiveData.value = it
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginPasswordLiveData.value = Resource.Loading()
            userUseCase.login(email, password).collect {
                when (it) {
                    is Resource.Success -> {
                        preferences.accessToken = it.data?.accessToken
                        getUser(email)
                    }
                    is Resource.DataError -> _loginPasswordLiveData.value = it
                }
            }
        }
    }

    private fun getUser(email: String) {
        viewModelScope.launch {
            userUseCase.getUser(email).collect {
                _getUserLiveData.value = it
                when (it) {
                    is Resource.Success -> {
                        preferences.profile = it.data?.toProfileModel()
                    }
                }
            }
        }
    }

    fun showError(errorCode: Int) {
        _errorLiveData.value = errorManager.getError(errorCode).description
    }
}