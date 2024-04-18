package com.example.csc_mvvm.ui.component.product.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.csc_mvvm.R
import com.example.csc_mvvm.app.BundleKey
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.cart.CartProductModel
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.databinding.FragmentProductDetailBinding
import com.example.csc_mvvm.ui.base.BaseFragment
import com.example.csc_mvvm.ui.component.product.ProductViewModel
import com.example.csc_mvvm.ui.component.product.adapter.ProductsHorizontalAdapter
import com.example.csc_mvvm.ui.component.product.navigate.NavigatorProduct
import com.example.csc_mvvm.ui.dialog.AddToCartDialog
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.hide
import com.example.csc_mvvm.utils.show
import java.text.NumberFormat
import java.util.Locale
class ProductDetailFragment : BaseFragment() {

    private var scrollTop = true

    companion object {
        fun newInstance(args: Bundle?) = ProductDetailFragment().apply {
            arguments = args
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentProductDetailBinding::inflate
    private val binding by lazy { binding<FragmentProductDetailBinding>() }
    private val productViewModel: ProductViewModel by activityViewModels()
    private val product by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getSerializable(BundleKey.PRODUCT_MODEL, ProductModel::class.java)
        else
            arguments?.getSerializable(BundleKey.PRODUCT_MODEL) as? ProductModel
    }

    override fun observeViewModel() {
        productViewModel.showDialogAddToCartLiveData.observe(viewLifecycleOwner) {
            showAddToCartDialog(it)
        }
        productViewModel.cartLiveData.observe(viewLifecycleOwner) {
            showCart(it)
        }
        productViewModel.productsLiveData.observe(viewLifecycleOwner) {
            showProducts(it)
        }
    }

    override fun bindData() {
        productViewModel.getCart()
    }

    override fun bindComponent() {
        binding.apply {
            product?.let {
                Glide.with(this@ProductDetailFragment).asBitmap().placeholder(R.drawable.ic_category_default)
                    .load(it.avatar)
                    .into(viewIcon)
                tvTitle.text = it.name
                tvName.text = it.name
                tvPrice.text = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(it.price)
                tvDescription.text = it.description
                if (it.quantity > 1) {
                    tvAddToCart.isEnabled = true
                    tvAddToCart.setBackgroundResource(R.drawable.bg_btn)
                    tvQuantity.text = getString(R.string.text_product_quantity, it.quantity)
                } else {
                    tvAddToCart.isEnabled = false
                    tvAddToCart.setBackgroundResource(R.drawable.bg_btn_disable)
                    tvQuantity.text = getString(R.string.text_product_quantity_0)
                }
            }
        }
    }

    override fun bindEvent() {
        binding.apply {
            imvBack.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
            cvCartPlace.setOnClickListener {

            }
            tvAddToCart.setOnClickListener {
                productViewModel.showDialogAddToCart(product ?: return@setOnClickListener)
            }
            tvMore.setOnClickListener {
                NavigatorProduct.showProductScreen(arguments?.apply {
                    putSerializable(BundleKey.CATEGORY_MODEL, product?.category ?: return@setOnClickListener)
                })
            }
            appbarLayout.addOnOffsetChangedListener { _, verticalOffset ->
                when (verticalOffset) {
                    -collapsingToolbar.height + toolbar.height -> {
                        tvTitle.show()
                        imvBack.setColorFilter(Color.BLACK)
                        imvShare.setColorFilter(Color.BLACK)
                        scrollTop = false
                    }

                    else -> {
                        if (!scrollTop) {
                            tvTitle.hide()
                            imvBack.setColorFilter(Color.WHITE)
                            imvShare.setColorFilter(Color.WHITE)
                            scrollTop = true
                        }
                    }
                }
            }
        }
    }
    private fun showAddToCartDialog(product: ProductModel) {
        AddToCartDialog(product, {
            productViewModel.addToCart(it)
        }, {
            NavigatorProduct.popFragment()
            NavigatorProduct.showProductDetailScreen(arguments?.apply {
                putSerializable(BundleKey.PRODUCT_MODEL, product)
            })
        }).show(childFragmentManager)
    }
    private fun showCart(products: List<CartProductModel>) {
        binding.apply {
            if (products.isNotEmpty()) {
                rllCart.show()
                tvCartQuantity.text = products.size.toString()
            } else {
                rllCart.gone()
            }
        }
    }

    private fun showProducts(status: Pair<Resource<List<ProductModel>>, Int>) {
        binding.apply {
            when (status.first) {
                is Resource.Loading -> rllLoading.show()
                is Resource.Success -> {
                    rllLoading.gone()
                    status.first.data?.let { products ->
                        if (products.isEmpty() || product == null) {
                            imvEmpty.show()
                            recyclerView.gone()
                        } else {
                            imvEmpty.gone()
                            recyclerView.show()
                            recyclerView.layoutManager = LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
                            val productsHorizontalAdapter =
                                ProductsHorizontalAdapter(
                                    products + ProductModel(),
                                    product!!.category,
                                    productViewModel
                                )
                            recyclerView.adapter = productsHorizontalAdapter
                        }
                    }
                }
                is Resource.DataError -> {
                    imvEmpty.show()
                    recyclerView.gone()
                    rllLoading.gone()
                }
            }
        }

    }

}