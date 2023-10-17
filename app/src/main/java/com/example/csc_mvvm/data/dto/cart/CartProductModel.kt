package com.example.csc_mvvm.data.dto.cart

import com.example.csc_mvvm.data.dto.order.OrderDetailModel
import com.example.csc_mvvm.data.dto.product.ProductModel


data class CartProductModel(
    var product: ProductModel,
    var quantity: Int,
) {
    fun getPrice() = product.price * quantity
}

fun List<CartProductModel>.toOrderDetails() =
    map { OrderDetailModel(it.quantity, it.product.price, it.product) }