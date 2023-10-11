package com.example.csc_mvvm.usecase.errors

import com.example.csc_mvvm.data.error.Error
import com.example.csc_mvvm.data.error.mapper.ErrorMapper


class ErrorManager(private val errorMapper:ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }

}
