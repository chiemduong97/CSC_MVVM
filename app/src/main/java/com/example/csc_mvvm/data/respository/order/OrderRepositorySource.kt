package com.example.csc_mvvm.data.respository.order

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest
import kotlinx.coroutines.flow.Flow

interface OrderRepositorySource {
    suspend fun requestCreateOrder(orderRequest: OrderRequest): Flow<Resource<DataOrderResponse>>
    suspend fun requestGetOrder(orderCode: String): Flow<Resource<OrderModel>>
    suspend fun requestGetOrdersByUser(
        userId: Int,
        page: Int,
        limit: Int
    ): Flow<Resource<List<OrderModel>>>

    suspend fun requestDestroyOrder(orderCode: String, status: Int): Flow<Resource<DataOrderResponse>>
    suspend fun requestGetCountOrder(userId: Int): Flow<Resource<DataCountOrder>>
}