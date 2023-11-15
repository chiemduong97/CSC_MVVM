package com.example.csc_mvvm.usecase

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.order.RemoteOrderData
import com.example.csc_mvvm.data.remote.service.OrderService
import com.example.csc_mvvm.data.respository.order.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class OrderUseCase {
    private val orderRepository by lazy {
        OrderRepository(
            RemoteOrderData(ServiceGenerator.newInstance().create(OrderService::class.java)),
            Dispatchers.IO
        )
    }

    suspend fun getOrders(userId: Int, page: Int, limit: Int): Flow<Resource<List<OrderModel>>> {
        return orderRepository.requestGetOrdersByUser(userId, page, limit)
    }

    suspend fun createOrder(orderRequest: OrderRequest): Flow<Resource<DataOrderResponse>> {
        return orderRepository.requestCreateOrder(orderRequest)
    }

    suspend fun getOrder(orderCode: String): Flow<Resource<OrderModel>> {
        return orderRepository.requestGetOrder(orderCode)
    }

    suspend fun destroyOrder(orderCode: String, status: Int): Flow<Resource<DataOrderResponse>> {
        return orderRepository.requestDestroyOrder(orderCode, status)
    }

    suspend fun getCountOrder(userId: Int): Flow<Resource<DataCountOrder>> {
        return orderRepository.requestGetCountOrder(userId)
    }
}