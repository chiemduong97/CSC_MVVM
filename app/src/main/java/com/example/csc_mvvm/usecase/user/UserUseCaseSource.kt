package com.example.csc_mvvm.usecase.user

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import kotlinx.coroutines.flow.Flow

interface UserUseCaseSource {

    suspend fun checkEmail(email: String): Flow<Resource<Any>>

    suspend fun login(email: String, password: String): Flow<Resource<DataProfileResponse>>

    suspend fun getUser(email: String): Flow<Resource<ProfileModel>>

    suspend fun getUserFromLocal(): Flow<Resource<ProfileModel>>

}