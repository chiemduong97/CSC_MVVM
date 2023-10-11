package com.example.csc_mvvm.data.error.mapper

import android.content.Context
import com.example.csc_mvvm.R
import com.example.csc_mvvm.app.ErrorCode

class ErrorMapper(private val context: Context) : ErrorMapperSource {
    override fun getErrorString(errorId: Int) = context.getString(errorId)

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(ErrorCode.ERROR_1001, getErrorString(R.string.err_code_1001)),
            Pair(ErrorCode.ERROR_1002, getErrorString(R.string.err_code_1002)),
            Pair(ErrorCode.ERROR_1003, getErrorString(R.string.err_code_1003)),
            Pair(ErrorCode.ERROR_1004, getErrorString(R.string.err_code_1004)),
            Pair(ErrorCode.ERROR_1005, getErrorString(R.string.err_code_1005)),
            Pair(ErrorCode.ERROR_1006, getErrorString(R.string.err_code_1006)),
            Pair(ErrorCode.ERROR_1007, getErrorString(R.string.err_code_1007)),
            Pair(ErrorCode.ERROR_1008, getErrorString(R.string.err_code_1008)),
            Pair(ErrorCode.ERROR_1009, getErrorString(R.string.err_code_1009)),
            Pair(ErrorCode.ERROR_1010, getErrorString(R.string.err_code_1010)),
            Pair(ErrorCode.ERROR_1011, getErrorString(R.string.err_code_1011)),
            Pair(ErrorCode.ERROR_1012, getErrorString(R.string.err_code_1012)),
            Pair(ErrorCode.ERROR_1013, getErrorString(R.string.err_code_1013)),
            Pair(ErrorCode.ERROR_1014, getErrorString(R.string.err_code_1014)),
            Pair(ErrorCode.ERROR_1015, getErrorString(R.string.err_code_1015)),
            Pair(ErrorCode.ERROR_1016, getErrorString(R.string.err_code_1016)),
            Pair(ErrorCode.ERROR_1017, getErrorString(R.string.err_code_1017)),
            Pair(ErrorCode.ERROR_1018, getErrorString(R.string.err_code_1018)),
            Pair(ErrorCode.ERROR_1019, getErrorString(R.string.err_code_1019)),
        ).withDefault { getErrorString(R.string.err_code_1001) }
}