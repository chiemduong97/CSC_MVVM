package com.example.csc_mvvm.usecase

import com.example.client.api.service.UserService
import com.example.client.models.profile.DataProfileResponse
import com.example.client.models.profile.ProfileResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.user.RemoteUserData
import com.example.csc_mvvm.data.respository.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserUseCase {
    private val userRepository by lazy {
        UserRepository(
            RemoteUserData(ServiceGenerator.newInstance().create(UserService::class.java)),
            Dispatchers.Default
        )
    }

    fun checkEmail(email: String): Flow<Resource<Any>> {
        return userRepository.doCheckEmail(email)
    }

    fun login(email: String, password: String): Flow<Resource<DataProfileResponse>> {
        return userRepository.doLogin(email, password)
    }

    fun getUser(email: String): Flow<Resource<ProfileResponse>> {
        return userRepository.doGetUser(email)
    }

}