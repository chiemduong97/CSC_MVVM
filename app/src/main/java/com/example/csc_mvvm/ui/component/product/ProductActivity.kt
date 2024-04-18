package com.example.csc_mvvm.ui.component.product

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.csc_mvvm.R
import com.example.csc_mvvm.app.BundleKey
import com.example.csc_mvvm.app.SHOW_PRODUCT_DETAIL
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.databinding.ActivityEmptyBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.ui.component.product.detail.ProductDetailFragment
import com.example.csc_mvvm.ui.component.product.navigate.INavigateProduct
import com.example.csc_mvvm.ui.component.product.navigate.NavigatorProduct
import com.example.csc_mvvm.ui.component.product.products.ProductsFragment
import com.example.csc_mvvm.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity(), INavigateProduct {

    companion object {
        @JvmStatic
        fun newInstance(from: Activity, categoryModel: CategoryModel): Intent =
            Intent(from, ProductActivity::class.java).apply {
                putExtra(BundleKey.CATEGORY_MODEL, categoryModel)
                putExtra(SHOW_PRODUCT_DETAIL, false)
            }

        @JvmStatic
        fun newInstance(from: Activity, productModel: ProductModel, showProductDetail: Boolean) =
            Intent(from, ProductActivity::class.java).apply {
                putExtra(BundleKey.PRODUCT_MODEL, productModel)
                putExtra(SHOW_PRODUCT_DETAIL, showProductDetail)
            }
    }

    private lateinit var binding: ActivityEmptyBinding
    private val showProductDetail by lazy {
        intent?.getBooleanExtra(SHOW_PRODUCT_DETAIL, false) ?: false
    }

    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigatorProduct.onStart(this)
    }

    override fun bindComponent() {
        if (showProductDetail) addFragment(
            ProductDetailFragment.newInstance(
                intent?.extras
                    ?: Bundle()
            ), ProductDetailFragment::class.java.simpleName
        )
        else addFragment(
            ProductsFragment.newInstance(
                intent?.extras
                    ?: Bundle()
            ), ProductsFragment::class.java.simpleName
        )
    }

    override fun bindEvent() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                supportFragmentManager.run {
                    if (fragments.size == 1 || backStackEntryCount == 1) {
                        finish()
                    } else {
                        ActivityUtils.popFragment(this)
                    }
                }
            }
        })
    }

    override fun replaceFragment(fragment: Fragment?, tag: String) {
        fragment ?: return
        ActivityUtils.replaceFragmentInActivity(
            supportFragmentManager,
            fragment,
            R.id.container,
            tag
        )
    }

    override fun addFragment(fragment: Fragment?, tag: String) {
        fragment ?: return
        ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.container, tag)
    }

    override fun popFragment() {
        ActivityUtils.popFragment(supportFragmentManager)
    }

}