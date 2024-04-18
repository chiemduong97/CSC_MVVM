package com.example.csc_mvvm.data.remote.user

import com.example.client.api.service.UserService
import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.dto.profile.ProfileResponse
import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import com.example.csc_mvvm.data.remote.ServiceGenerator
import retrofit2.Call
import retrofit2.awaitResponse
import javax.inject.Inject

class RemoteUserData @Inject constructor(private val serviceGenerator: ServiceGenerator) : RemoteUserDataSource {
    private val service by lazy { serviceGenerator.newInstance().create(UserService::class.java) }

    private suspend fun processCall(responseCall: () -> Call<*>): Any? {
        val response = responseCall.invoke().awaitResponse()
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.code()
        }
    }

    override suspend fun requestCheckEmail(email: String): Resource<Any> {
        return when (val response = processCall { service.checkEmail(email) }) {
            is BaseResponse<*> -> {
                if (response.isError) {
                    Resource.DataError(errorCode = response.code)
                } else {
                    Resource.Success(data = Any())
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestLogin(
        email: String,
        password: String
    ): Resource<DataProfileResponse> {
        return when (val response = processCall { service.login(email, password) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is DataProfileResponse -> Resource.Success(data = response.data as DataProfileResponse)
                    else -> Resource.DataError(errorCode = 1001)
                }
            }
            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestGetUser(email: String): Resource<ProfileModel> {
        return when (val response = processCall { service.getUserByEmail(email) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is ProfileResponse -> Resource.Success(data = (response.data as ProfileResponse).toProfileModel())
                    else -> Resource.DataError(errorCode = 1001)
                }
            }
            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }
}