package com.example.csc_mvvm.data.remote.order

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderModel
import com.example.csc_mvvm.data.dto.order.OrderRequest
import com.example.csc_mvvm.data.dto.order.OrderResponse
import com.example.csc_mvvm.data.dto.order.toOrders
import com.example.csc_mvvm.data.dto.order.toOrdersResponse
import com.example.csc_mvvm.data.remote.ServiceGenerator
import com.example.csc_mvvm.data.remote.service.OrderService
import retrofit2.Call
import retrofit2.awaitResponse
import javax.inject.Inject

class RemoteOrderData @Inject constructor(private val serviceGenerator: ServiceGenerator) : RemoteOrderDataSource {
    private val service by lazy { serviceGenerator.newInstance().create(OrderService::class.java) }

    private suspend fun processCall(responseCall: () -> Call<*>): Any? {
        val response = responseCall.invoke().awaitResponse()
        return if (response.isSuccessful) {
            response.body()
        } else {
            response.code()
        }
    }

    override suspend fun requestCreateOrder(orderRequest: OrderRequest): Resource<DataOrderResponse> {
        return when (val response = processCall { service.createOrder(orderRequest) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is DataOrderResponse -> Resource.Success(data = response.data as DataOrderResponse)
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestGetOrder(orderCode: String): Resource<OrderModel> {
        return when (val response = processCall { service.getOrder(orderCode) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is OrderResponse -> Resource.Success(data = (response.data as OrderResponse).toOrderModel())
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestGetOrdersByUser(
        userId: Int,
        page: Int,
        limit: Int
    ): Resource<List<OrderModel>> {
        return when (val response = processCall { service.getOrdersByUser(userId, page, limit) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is List<*> -> Resource.Success(data = (response.data as List<*>).toOrdersResponse().toOrders())
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestDestroyOrder(
        orderCode: String,
        status: Int
    ): Resource<DataOrderResponse> {
        return when (val response = processCall { service.destroyOrder(orderCode, status) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is DataOrderResponse -> Resource.Success(data = response.data as DataOrderResponse)
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }

    override suspend fun requestGetCountOrder(userId: Int): Resource<DataCountOrder> {
        return when (val response = processCall { service.getCountOrder(userId) }) {
            is BaseResponse<*> -> {
                when {
                    response.isError -> Resource.DataError(errorCode = response.code)
                    response.data is DataCountOrder -> Resource.Success(data = response.data as DataCountOrder)
                    else -> Resource.DataError(errorCode = 1001)
                }
            }

            else -> {
                Resource.DataError(errorCode = 1001)
            }
        }
    }
}