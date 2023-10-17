package com.example.csc_mvvm.data.respository.user

import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.dto.profile.ProfileRequest
import com.example.csc_mvvm.app.RequestType
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import com.example.csc_mvvm.data.local.LocalData
import com.example.csc_mvvm.data.remote.user.RemoteUserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class UserRepository(
    private val remoteData: RemoteUserData,
    private val localData: LocalData,
    private val context: CoroutineContext
) : UserRepositorySource {
    override fun doCheckEmail(email: String): Flow<Resource<Any>> =
        flow {
            emit(remoteData.requestCheckEmail(email))
        }.flowOn(context)

    override fun doRegister(
        fullName: String,
        phone: String,
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun doLogin(email: String, password: String): Flow<Resource<DataProfileResponse>> =
        flow {
            emit(remoteData.requestLogin(email, password))
        }.flowOn(context)

    override fun doGetUser(email: String): Flow<Resource<ProfileModel>> =
        flow {
            emit(remoteData.requestGetUser(email))
        }.flowOn(context)

    override fun doGetUserFromLocal(): Flow<Resource<ProfileModel>> =
        flow {
            emit(localData.getUser())
        }.flowOn(context)

    override fun updateInfo(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun updatePass(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun updateAvatar(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun updateDeviceToken(
        email: String,
        deviceToken: String
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun sendEmail(
        email: String,
        phone: String,
        requestType: RequestType
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun verification(email: String, code: String): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun resetPassword(
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }
}