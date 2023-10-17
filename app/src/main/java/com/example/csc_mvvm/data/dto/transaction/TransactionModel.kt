package com.example.csc_mvvm.data.dto.transaction

import com.example.csc_mvvm.app.TransactionType
import com.example.csc_mvvm.base.BaseModel


data class TransactionModel(
    var transid: String,
    var transid_momo: String?,
    var created_at: String,
    var type: TransactionType,
    var amount: Double,
    var user_id: Int,
    var status: Int,
    var order_code: String?,
) : BaseModel()

data class TransactionResponse(
    var transid: String?,
    var transid_momo: String?,
    var created_at: String?,
    var type: TransactionType?,
    var amount: Double?,
    var user_id: Int?,
    var status: Int?,
    var order_code: String?,
) : BaseModel() {
    fun toTransactionModel() = TransactionModel(
        transid = transid.orEmpty(),
        transid_momo = transid_momo,
        created_at = created_at.orEmpty(),
        type = type ?: TransactionType.PAID,
        amount = amount ?: 0.0,
        user_id = user_id ?: -1,
        status = status ?: 0,
        order_code = order_code
    )
}

fun List<TransactionResponse>.toTransactions() = map { it.toTransactionModel() }

data class DataTransactionResponse(val order_code: String)
