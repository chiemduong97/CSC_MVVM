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
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepository @Inject constructor(
    private val remoteData: RemoteUserData,
    private val localData: LocalData,
    private val context: CoroutineContext
) : UserRepositorySource {
    override suspend fun doCheckEmail(email: String): Flow<Resource<Any>> =
        flow {
            emit(remoteData.requestCheckEmail(email))
        }.flowOn(context)

    override suspend fun doRegister(
        fullName: String,
        phone: String,
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun doLogin(email: String, password: String): Flow<Resource<DataProfileResponse>> =
        flow {
            emit(remoteData.requestLogin(email, password))
        }.flowOn(context)

    override suspend fun doGetUser(email: String): Flow<Resource<ProfileModel>> =
        flow {
            emit(remoteData.requestGetUser(email))
        }.flowOn(context)

    override suspend fun doGetUserFromLocal(): Flow<Resource<ProfileModel>> =
        flow {
            emit(localData.getUser())
        }.flowOn(context)

    override suspend fun updateInfo(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePass(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAvatar(profileRequest: ProfileRequest): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateDeviceToken(
        email: String,
        deviceToken: String
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendEmail(
        email: String,
        phone: String,
        requestType: RequestType
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun verification(email: String, code: String): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>> {
        TODO("Not yet implemented")
    }
}