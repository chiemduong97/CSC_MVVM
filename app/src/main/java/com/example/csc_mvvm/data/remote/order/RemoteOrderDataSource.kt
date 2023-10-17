package com.example.csc_mvvm.data.remote.order

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest

interface RemoteOrderDataSource {
    suspend fun requestCreateOrder(orderRequest: OrderRequest): Resource<DataOrderResponse>

    suspend fun requestGetOrder(orderCode: String): Resource<OrderModel>

    suspend fun requestGetOrdersByUser(userId: Int, page: Int, limit: Int): Resource<List<OrderModel>>

    suspend fun requestDestroyOrder(orderCode: String, status: Int): Resource<DataOrderResponse>

    suspend fun requestGetCountOrder(userId: Int): Resource<DataCountOrder>
}