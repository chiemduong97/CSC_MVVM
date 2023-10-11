package com.example.csc_mvvm.data.respository.user

import com.example.client.models.profile.DataProfileResponse
import com.example.client.models.profile.ProfileRequest
import com.example.client.models.profile.ProfileResponse
import com.example.csc_mvvm.app.RequestType
import com.example.csc_mvvm.data.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepositorySource {
    fun doCheckEmail(email: String): Flow<Resource<Any>>

    fun doRegister(
        fullName: String,
        phone: String,
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>>

    fun doLogin(
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>>

    fun doGetUser(email: String): Flow<Resource<ProfileResponse>>

    fun updateInfo(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>>

    fun updatePass(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>>

    fun updateAvatar(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>>

    fun updateDeviceToken(email: String, deviceToken: String): Flow<Resource<DataProfileResponse>>


    fun sendEmail(
        email: String,
        phone: String,
        requestType: RequestType,
    ): Flow<Resource<DataProfileResponse>>

    fun verification(email: String, code: String): Flow<Resource<DataProfileResponse>>

    fun resetPassword(email: String, password: String): Flow<Resource<DataProfileResponse>>
}