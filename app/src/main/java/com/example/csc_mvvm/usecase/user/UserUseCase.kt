package com.example.csc_mvvm.usecase.user

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import com.example.csc_mvvm.data.respository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UserUseCaseSource {

    override suspend fun checkEmail(email: String): Flow<Resource<Any>> {
        return userRepository.doCheckEmail(email)
    }

    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<DataProfileResponse>> {
        return userRepository.doLogin(email, password)
    }

    override suspend fun getUser(email: String): Flow<Resource<ProfileModel>> {
        return userRepository.doGetUser(email)
    }

    override suspend fun getUserFromLocal(): Flow<Resource<ProfileModel>> {
        return userRepository.doGetUserFromLocal()
    }

}