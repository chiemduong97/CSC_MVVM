package com.example.csc_mvvm.data.remote.branch

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.google.android.gms.maps.model.LatLng

interface RemoteBranchDataSource {
    suspend fun requestGetBranches(userLocation: LatLng, branchId: Int): Resource<Pair<List<BranchModel>, Int>>

}