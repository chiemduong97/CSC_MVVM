package com.example.csc_mvvm.ui.component.product.products

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.csc_mvvm.app.BundleKey
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.databinding.FragmentProductsBinding
import com.example.csc_mvvm.ui.base.BaseCollectionFragment
import com.example.csc_mvvm.ui.component.product.ProductViewModel
import com.example.csc_mvvm.ui.component.product.adapter.ProductsVerticalAdapter
import com.example.csc_mvvm.ui.component.product.navigate.NavigatorProduct
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show

class ProductsFragment : BaseCollectionFragment<ProductViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle?) = ProductsFragment().apply {
            arguments = args
        }
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentProductsBinding::inflate
    private val binding by lazy { binding<FragmentProductsBinding>() }
    override val viewModel: ProductViewModel by activityViewModels()
    private val mLayoutManager by lazy { LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) }
    private val category by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(BundleKey.CATEGORY_MODEL, CategoryModel::class.java)
        } else {
            arguments?.getSerializable(BundleKey.CATEGORY_MODEL) as? CategoryModel
        }
    }

    private var mProducts = arrayListOf<ProductModel>()

    override fun observeViewModel() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            showProducts(it)
        }
        viewModel.moreProductsLiveData.observe(viewLifecycleOwner) {
            showMoreProducts(it)
        }
        viewModel.openProductDetailLiveData.observe(viewLifecycleOwner) {
            navigateToProductDetailScreen(it)
        }
    }

    override fun onRefresh() {
        bindData()
    }

    override fun bindComponent() {
        binding.tvTitle.text = category?.name
    }

    override fun bindData() {
        category?.let {
            viewModel.getProducts(it)
        }
    }


    override fun bindEvent() {
        binding.imvBack.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                activity?.onBackPressedDispatcher?.onBackPressed()
            else
                activity?.onBackPressed()
        }
    }

    private fun showProducts(status: Pair<Resource<List<ProductModel>>, Int>) {
        when (status.first) {
            is Resource.Loading -> showLoading()
            is Resource.Success -> {
                hideLoading()
                binding.apply {
                    status.first.data?.let { products ->
                        if (products.isNotEmpty()) {
                            mProducts.clear()
                            mProducts.addAll(products)
                            imvEmpty.gone()
                            recyclerView.run {
                                show()
                                layoutManager = mLayoutManager
                                adapter = ProductsVerticalAdapter(
                                    viewLifecycleOwner,
                                    mProducts,
                                    viewModel
                                )
                            }
                        } else {
                            imvEmpty.show()
                        }
                    }
                }
            }
            is Resource.DataError -> {
                hideLoading()
                binding.imvEmpty.show()
                binding.recyclerView.gone()
                status.first.errorCode?.let { viewModel.showError(it) }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showMoreProducts(status: Pair<Resource<List<ProductModel>>, Int>) {
        when (status.first) {
            is Resource.Loading -> showLoading()
            is Resource.Success -> {
                hideLoading()
                binding.apply {
                    status.first.data?.let { products ->
                        mProducts.addAll(products)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
            is Resource.DataError -> hideLoading()
        }
    }

    private fun navigateToProductDetailScreen(productModel: ProductModel) {
        NavigatorProduct.showProductDetailScreen(arguments?.apply {
            putSerializable(BundleKey.PRODUCT_MODEL, productModel)
        })
    }

    override fun shouldLoadMore() = true
}