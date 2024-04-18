package com.example.csc_mvvm.usecase.order

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest
import com.example.csc_mvvm.data.respository.order.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderUseCase @Inject constructor(private val orderRepository: OrderRepository): OrderUseCaseSource {

    override suspend fun getOrders(userId: Int, page: Int, limit: Int): Flow<Resource<List<OrderModel>>> {
        return orderRepository.requestGetOrdersByUser(userId, page, limit)
    }

    override suspend fun createOrder(orderRequest: OrderRequest): Flow<Resource<DataOrderResponse>> {
        return orderRepository.requestCreateOrder(orderRequest)
    }

    override suspend fun getOrder(orderCode: String): Flow<Resource<OrderModel>> {
        return orderRepository.requestGetOrder(orderCode)
    }

    override suspend fun destroyOrder(orderCode: String, status: Int): Flow<Resource<DataOrderResponse>> {
        return orderRepository.requestDestroyOrder(orderCode, status)
    }

    override suspend fun getCountOrder(userId: Int): Flow<Resource<DataCountOrder>> {
        return orderRepository.requestGetCountOrder(userId)
    }
}