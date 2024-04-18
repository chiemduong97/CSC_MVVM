package com.example.csc_mvvm.usecase.order

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest
import kotlinx.coroutines.flow.Flow

interface OrderUseCaseSource {

    suspend fun getOrders(userId: Int, page: Int, limit: Int): Flow<Resource<List<OrderModel>>>

    suspend fun createOrder(orderRequest: OrderRequest): Flow<Resource<DataOrderResponse>>

    suspend fun getOrder(orderCode: String): Flow<Resource<OrderModel>>

    suspend fun destroyOrder(orderCode: String, status: Int): Flow<Resource<DataOrderResponse>>

    suspend fun getCountOrder(userId: Int): Flow<Resource<DataCountOrder>>
}