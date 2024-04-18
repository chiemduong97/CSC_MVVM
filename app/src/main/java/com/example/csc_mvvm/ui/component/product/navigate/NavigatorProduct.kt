package com.example.csc_mvvm.ui.component.product.navigate

import android.os.Bundle
import com.example.csc_mvvm.ui.component.product.detail.ProductDetailFragment
import com.example.csc_mvvm.ui.component.product.products.ProductsFragment

object NavigatorProduct {

    private var mView: INavigateProduct? = null

    @JvmStatic
    fun onStart(view: INavigateProduct?) {
        mView = view
    }

    @JvmStatic
    fun popFragment() {
        mView?.popFragment()
    }

    @JvmStatic
    fun showProductScreen(args: Bundle?) {
        mView?.addFragment(ProductsFragment.newInstance(args), ProductsFragment::class.java.simpleName)
    }

    @JvmStatic
    fun showProductDetailScreen(args: Bundle?) {
        mView?.addFragment(ProductDetailFragment.newInstance(args), ProductDetailFragment::class.java.simpleName)
    }

}