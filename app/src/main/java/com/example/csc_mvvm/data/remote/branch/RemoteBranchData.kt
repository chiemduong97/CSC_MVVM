package com.example.csc_mvvm.data.remote.branch

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.dto.branch.toBranches
import com.example.csc_mvvm.data.dto.branch.toBranchesResponse
import com.example.csc_mvvm.data.remote.service.BranchService
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.awaitResponse

class RemoteBranchData(private val service: BranchService) : RemoteBranchDataSource {

    private suspend fun processCall(responseCall: () -> Call<*>): Any? {
        val response = responseCall.invoke().awaitResponse()
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.code()
        }
    }

    override suspend fun requestGetBranches(
        userLocation: LatLng,
        branchId: Int
    ): Resource<Pair<List<BranchModel>, Int>> {
        return when (val response = processCall { service.getBranches() }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(
                        Pair((response.data as List<*>).toBranchesResponse().toBranches(userLocation).sortedBy { it.distance }, branchId)
                    )

                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }
}