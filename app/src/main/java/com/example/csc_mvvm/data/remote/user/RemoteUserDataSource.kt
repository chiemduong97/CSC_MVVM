package com.example.csc_mvvm.data.remote.user

import com.example.client.models.profile.DataProfileResponse
import com.example.client.models.profile.ProfileResponse
import com.example.csc_mvvm.data.Resource

interface RemoteUserDataSource {
    suspend fun requestCheckEmail(email: String): Resource<Any>
    suspend fun requestLogin(email: String, password: String): Resource<DataProfileResponse>
    suspend fun requestGetUser(email: String): Resource<ProfileResponse>
}