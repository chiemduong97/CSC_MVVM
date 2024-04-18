package com.example.csc_mvvm.usecase.branch

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import kotlinx.coroutines.flow.Flow

interface BranchUseCaseSource {

    suspend fun getBranches(): Flow<Resource<Pair<List<BranchModel>, Int>>>

    suspend fun getBranchFromLocal(): Flow<Resource<BranchModel>>
}