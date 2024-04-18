package com.example.csc_mvvm.ui.component.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.category.HomeSectionModel
import com.example.csc_mvvm.data.dto.loading.LoadingMode
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.databinding.ItemProductsBinding
import com.example.csc_mvvm.ui.component.product.ProductViewModel
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show

class ProductSectionsAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val categories: List<CategoryModel>,
    private val productViewModel: ProductViewModel
) : RecyclerView.Adapter<ProductSectionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSectionsViewHolder {
        return ProductSectionsViewHolder(
            ItemProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ProductSectionsViewHolder, position: Int) {
        holder.bind(viewLifecycleOwner, categories[position], productViewModel)
    }
}

class ProductSectionsViewHolder(private val binding: ItemProductsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        viewLifecycleOwner: LifecycleOwner,
        category: CategoryModel,
        productViewModel: ProductViewModel
    ) {
        binding.apply {
            productViewModel.productsLiveData.observe(viewLifecycleOwner) { status ->
                showProducts(status, category, productViewModel)
            }
            tvMore.setOnClickListener { productViewModel.seeAllProducts(category) }
            if (category is HomeSectionModel) {
                tvName.text = category.title
                productViewModel.getProductsByUrl(category.id, category.url, 1, 10, LoadingMode.LOAD)
            } else {
                tvName.text = category.name
                productViewModel.getProductsByCategory(category.id, 1, 10, LoadingMode.LOAD)
            }
        }
    }

    private fun showProducts(
        status: Pair<Resource<List<ProductModel>>, Int>,
        category: CategoryModel,
        productViewModel: ProductViewModel
    ) {
        binding.apply {
            if (status.second == category.id) {
                when (status.first) {
                    is Resource.Loading -> rllLoading.show()
                    is Resource.Success -> {
                        rllLoading.gone()
                        status.first.data?.let { products ->
                            if (products.isEmpty()) {
                                imvEmpty.show()
                                recyclerView.gone()
                            } else {
                                imvEmpty.gone()
                                recyclerView.show()
                                recyclerView.layoutManager =
                                    LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
                                val productsHorizontalAdapter =
                                    ProductsHorizontalAdapter(
                                        products + ProductModel(),
                                        category,
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
}