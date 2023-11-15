package com.example.csc_mvvm.data.respository.user

import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.dto.profile.ProfileRequest
import com.example.csc_mvvm.app.RequestType
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import kotlinx.coroutines.flow.Flow

interface UserRepositorySource {
    suspend fun doCheckEmail(email: String): Flow<Resource<Any>>

    suspend fun doRegister(
        fullName: String,
        phone: String,
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>>

    suspend fun doLogin(
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>>

    suspend fun doGetUser(email: String): Flow<Resource<ProfileModel>>

    suspend fun doGetUserFromLocal(): Flow<Resource<ProfileModel>>

    suspend fun updateInfo(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>>

    suspend fun updatePass(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>>

    suspend fun updateAvatar(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>>

    suspend fun updateDeviceToken(email: String, deviceToken: String): Flow<Resource<DataProfileResponse>>


    suspend fun sendEmail(
        email: String,
        phone: String,
        requestType: RequestType,
    ): Flow<Resource<DataProfileResponse>>

    suspend fun verification(email: String, code: String): Flow<Resource<DataProfileResponse>>

    suspend fun resetPassword(email: String, password: String): Flow<Resource<DataProfileResponse>>
}