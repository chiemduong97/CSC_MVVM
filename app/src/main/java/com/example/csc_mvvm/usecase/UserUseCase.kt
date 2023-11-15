package com.example.csc_mvvm.usecase

import com.example.client.api.service.UserService
import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.data.local.LocalData
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.user.RemoteUserData
import com.example.csc_mvvm.data.respository.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserUseCase {
    private val userRepository by lazy {
        UserRepository(
            RemoteUserData(ServiceGenerator.newInstance().create(UserService::class.java)),
            LocalData(Preferences.newInstance()),
            Dispatchers.IO
        )
    }

    suspend fun checkEmail(email: String): Flow<Resource<Any>> {
        return userRepository.doCheckEmail(email)
    }

    suspend fun login(email: String, password: String): Flow<Resource<DataProfileResponse>> {
        return userRepository.doLogin(email, password)
    }

    suspend fun getUser(email: String): Flow<Resource<ProfileModel>> {
        return userRepository.doGetUser(email)
    }

    suspend fun getUserFromLocal(): Flow<Resource<ProfileModel>> {
        return userRepository.doGetUserFromLocal()
    }

}