package com.example.csc_mvvm.data.respository.order

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest
import kotlinx.coroutines.flow.Flow

interface OrderRepositorySource {
    fun requestCreateOrder(orderRequest: OrderRequest): Flow<Resource<DataOrderResponse>>
    fun requestGetOrder(orderCode: String): Flow<Resource<OrderModel>>
    fun requestGetOrdersByUser(
        userId: Int,
        page: Int,
        limit: Int
    ): Flow<Resource<List<OrderModel>>>

    fun requestDestroyOrder(orderCode: String, status: Int): Flow<Resource<DataOrderResponse>>
    fun requestGetCountOrder(userId: Int): Flow<Resource<DataCountOrder>>
}