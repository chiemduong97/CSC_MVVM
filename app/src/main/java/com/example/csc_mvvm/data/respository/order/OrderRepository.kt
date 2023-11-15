package com.example.csc_mvvm.data.respository.order

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest
import com.example.csc_mvvm.data.remote.order.RemoteOrderData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class OrderRepository(
    private val remoteData: RemoteOrderData, private val context: CoroutineContext
) : OrderRepositorySource {
    override suspend fun requestCreateOrder(orderRequest: OrderRequest): Flow<Resource<DataOrderResponse>> =
        flow {
            emit(remoteData.requestCreateOrder(orderRequest))
        }.flowOn(context)

    override suspend fun requestGetOrder(orderCode: String): Flow<Resource<OrderModel>> = flow {
        emit(remoteData.requestGetOrder(orderCode))
    }.flowOn(context)

    override suspend fun requestGetOrdersByUser(
        userId: Int, page: Int, limit: Int
    ): Flow<Resource<List<OrderModel>>> = flow {
        emit(remoteData.requestGetOrdersByUser(userId, page, limit))
    }.flowOn(context)

    override suspend fun requestDestroyOrder(
        orderCode: String, status: Int
    ): Flow<Resource<DataOrderResponse>> = flow {
        emit(remoteData.requestDestroyOrder(orderCode, status))
    }.flowOn(context)

    override suspend fun requestGetCountOrder(userId: Int): Flow<Resource<DataCountOrder>> =
        flow {
            emit(remoteData.requestGetCountOrder(userId))
        }.flowOn(context)

}