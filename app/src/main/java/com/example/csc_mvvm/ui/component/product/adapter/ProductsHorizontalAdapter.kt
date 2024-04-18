package com.example.csc_mvvm.ui.component.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.csc_mvvm.R
import com.example.csc_mvvm.app.TYPE_ITEM
import com.example.csc_mvvm.app.TYPE_MORE
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.databinding.ItemProductHorizontalBinding
import com.example.csc_mvvm.databinding.ItemProductSeeMoreBinding
import com.example.csc_mvvm.ui.component.product.ProductViewModel
import com.example.csc_mvvm.utils.toVND

class ProductsHorizontalAdapter(
    private val products: List<ProductModel>,
    private val category: CategoryModel,
    private val productViewModel: ProductViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM)
            ProductHorizontalViewHolder(
                ItemProductHorizontalBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        else
            SeeMoreProductViewHolder(
                ItemProductSeeMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductHorizontalViewHolder -> {
                holder.bind(products[position], productViewModel)
            }
            is SeeMoreProductViewHolder -> {
                holder.bind(category, productViewModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == products.size - 1) {
            TYPE_MORE
        } else {
            TYPE_ITEM
        }
    }
}

class ProductHorizontalViewHolder(val binding: ItemProductHorizontalBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel, productViewModel: ProductViewModel) {
        binding.apply {
            Glide.with(root.context).asBitmap().placeholder(R.drawable.ic_category_default)
                .load(product.avatar).into(viewIcon)
            tvName.text = product.name
            tvPrice.text = product.price.toVND()
            if (product.quantity > 0) {
                tvQuantity.text =
                    root.context.getString(R.string.text_product_quantity, product.quantity)
                tvAddToCart.isEnabled = true
                tvAddToCart.setTextColor(ContextCompat.getColor(root.context, R.color.black))
            } else {
                tvQuantity.text = root.context.getString(R.string.text_product_quantity_0)
                tvAddToCart.isEnabled = false
                tvAddToCart.setTextColor(ContextCompat.getColor(root.context, R.color.gray_dark))
            }
            tvAddToCart.setOnClickListener {
                productViewModel.showDialogAddToCart(product)
            }
            itemView.setOnClickListener {
                productViewModel.openProductDetail(product)
            }
        }
    }
}

class SeeMoreProductViewHolder(val binding: ItemProductSeeMoreBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(category: CategoryModel, productViewModel: ProductViewModel) {
        binding.apply {
            root.setOnClickListener { productViewModel.seeAllProducts(category) }
            tvDescription.text = root.context.getString(R.string.product_see_more, category.name)
        }
    }
}