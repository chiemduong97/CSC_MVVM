package com.example.csc_mvvm.data.dto.order

import com.example.csc_mvvm.base.BaseModel
import com.example.csc_mvvm.data.dto.cart.CartProductModel
import com.example.csc_mvvm.data.dto.product.ProductModel

data class OrderDetailModel(
    val quantity: Int,
    val price: Double,
    val product: ProductModel,
) : BaseModel()

data class OrderDetailResponse(
    val quantity: Int?,
    val price: Double?,
    val product: ProductModel?,
) : BaseModel() {
    fun toOrderDetailModel() = OrderDetailModel(
        quantity = quantity ?: 0,
        price = price ?: 0.0,
        product = product ?: ProductModel()
    )
}

fun List<OrderDetailResponse>.toOrderDetails() = map { it.toOrderDetailModel() }

fun List<OrderDetailModel>.toCartProducts() = map {
    CartProductModel(ProductModel().apply {
        id = it.product.id
        name = it.product.name
        price = it.price
        addToCart = it.quantity
    }, it.quantity)
}
