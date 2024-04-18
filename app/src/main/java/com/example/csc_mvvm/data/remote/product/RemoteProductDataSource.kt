package com.example.csc_mvvm.data.remote.product

import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.cart.CartModel
import com.example.csc_mvvm.data.dto.product.ProductModel

interface RemoteProductDataSource {
    suspend fun requestGetByCategory(
        categoryId: Int,
        page: Int,
        limit: Int,
        cart: CartModel
    ): Resource<List<ProductModel>>

    suspend fun requestFilter(
        query: String,
        page: Int,
        limit: Int,
        cart: CartModel
    ): Resource<List<ProductModel>>

    suspend fun requestGetByUrl(
        url: String,
        page: Int,
        limit: Int,
        userId: Int,
        cart: CartModel
    ): Resource<List<ProductModel>>


}