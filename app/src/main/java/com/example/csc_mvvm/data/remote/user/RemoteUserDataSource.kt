package com.example.csc_mvvm.data.remote.user

import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.ProfileModel

interface RemoteUserDataSource {
    suspend fun requestCheckEmail(email: String): Resource<Any>
    suspend fun requestLogin(email: String, password: String): Resource<DataProfileResponse>
    suspend fun requestGetUser(email: String): Resource<ProfileModel>
}