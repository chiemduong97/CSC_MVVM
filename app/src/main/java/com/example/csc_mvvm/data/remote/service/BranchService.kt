package com.example.csc_mvvm.data.remote.service

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.dto.branch.BranchResponse
import retrofit2.Call
import retrofit2.http.GET

interface BranchService {
    @GET("api/branch/branch_getAll.php")
    fun getBranches(): Call<BaseResponse<List<BranchResponse>>>
}