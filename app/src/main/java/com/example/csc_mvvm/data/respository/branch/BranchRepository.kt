package com.example.csc_mvvm.data.respository.branch

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.local.LocalData
import com.example.csc_mvvm.data.remote.branch.RemoteBranchData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class BranchRepository(
    private val remoteData: RemoteBranchData,
    private val localData: LocalData,
    private val context: CoroutineContext
) : BranchRepositorySource {

    override fun requestBranches(): Flow<Resource<Pair<List<BranchModel>, Int>>> =
        flow {
            emit(
                remoteData.requestGetBranches(
                    LatLng(
                        localData.getOrderLocation().data!!.lat,
                        localData.getOrderLocation().data!!.lng
                    ),
                    localData.getBranch().data?.id ?: -1
                )
            )
        }.flowOn(context)

    override fun requestBranchFromLocal(): Flow<Resource<BranchModel>> =
        flow {
            emit(localData.getBranch())
        }.flowOn(context)

}