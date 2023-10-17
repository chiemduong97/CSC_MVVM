package com.example.csc_mvvm.data.remote.service

import com.example.csc_mvvm.base.BaseResponse
import com.example.csc_mvvm.data.dto.order.DataCountOrder
import com.example.csc_mvvm.data.dto.order.DataOrderResponse
import com.example.csc_mvvm.data.dto.order.OrderRequest
import com.example.csc_mvvm.data.dto.order.OrderResponse
import retrofit2.Call
import retrofit2.http.*

interface OrderService {
    @POST("api/order/order_create.php")
    fun createOrder(@Body orderRequest: OrderRequest): Call<BaseResponse<DataOrderResponse>>

    @GET("api/order/order_getByOrderCode.php")
    fun getOrder(@Query("order_code") orderCode: String): Call<BaseResponse<OrderResponse>>

    @GET("api/order/order_getByUser.php")
    fun getOrdersByUser(
        @Query("user_id") user_id: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Call<BaseResponse<List<OrderResponse>>>

    @FormUrlEncoded
    @POST("api/order/order_destroy.php")
    fun destroyOrder(
        @Field("order_code") orderCode: String,
        @Field("status") status: Int
    ): Call<BaseResponse<DataOrderResponse>>

    @GET("api/order/order_getCountByUser.php")
    fun getCountOrder(@Query("user_id") user_id: Int): Call<BaseResponse<DataCountOrder>>
}