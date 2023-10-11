package com.example.csc_mvvm.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    var data: T,
    var code: Int,
    @SerializedName("is_error")
    var isError: Boolean,
    var message: String,
    @SerializedName("load_morde")
    var loadMore: Boolean,
) : BaseModel()
