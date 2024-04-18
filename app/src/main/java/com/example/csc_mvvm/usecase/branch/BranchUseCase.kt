package com.example.csc_mvvm.usecase.branch

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.respository.branch.BranchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BranchUseCase @Inject constructor(private val branchRepository: BranchRepository) :
    BranchUseCaseSource {

    override suspend fun getBranches(): Flow<Resource<Pair<List<BranchModel>, Int>>> {
        return branchRepository.requestBranches()
    }

    override suspend fun getBranchFromLocal(): Flow<Resource<BranchModel>> {
        return branchRepository.requestBranchFromLocal()
    }
}