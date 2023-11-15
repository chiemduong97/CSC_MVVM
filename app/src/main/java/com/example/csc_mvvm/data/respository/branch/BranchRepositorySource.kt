package com.example.csc_mvvm.data.respository.branch

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import kotlinx.coroutines.flow.Flow

interface BranchRepositorySource {
    suspend fun requestBranches(): Flow<Resource<Pair<List<BranchModel>, Int>>>

    suspend fun requestBranchFromLocal(): Flow<Resource<BranchModel>>
}