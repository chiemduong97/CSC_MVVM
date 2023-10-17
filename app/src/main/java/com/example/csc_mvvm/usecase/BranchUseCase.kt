package com.example.csc_mvvm.usecase

import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.local.LocalData
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.branch.RemoteBranchData
import com.example.csc_mvvm.data.remote.service.BranchService
import com.example.csc_mvvm.data.respository.branch.BranchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class BranchUseCase {
    private val branchRepository by lazy {
        BranchRepository(
            RemoteBranchData(ServiceGenerator.newInstance().create(BranchService::class.java)),
            LocalData(Preferences.newInstance()),
            Dispatchers.Default
        )
    }

    fun getBranches(): Flow<Resource<Pair<List<BranchModel>, Int>>> {
        return branchRepository.requestBranches()
    }

    fun getBranchFromLocal(): Flow<Resource<BranchModel>> {
        return branchRepository.requestBranchFromLocal()
    }
}