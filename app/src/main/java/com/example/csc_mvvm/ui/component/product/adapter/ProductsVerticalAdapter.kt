package com.example.csc_mvvm.ui.component.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.csc_mvvm.R
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.databinding.ItemProductVerticalBinding
import com.example.csc_mvvm.ui.component.product.ProductViewModel
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show
import com.example.csc_mvvm.utils.toVND

class ProductsVerticalAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val products: List<ProductModel>,
    private val productViewModel: ProductViewModel
) : RecyclerView.Adapter<ProductVerticalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductVerticalViewHolder(
            ItemProductVerticalBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductVerticalViewHolder, position: Int) {
        holder.bind(viewLifecycleOwner, products[position], productViewModel)
    }
}

class ProductVerticalViewHolder(val binding: ItemProductVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        viewLifecycleOwner: LifecycleOwner,
        product: ProductModel,
        productViewModel: ProductViewModel
    ) {
        binding.apply {
            productViewModel.cartLiveData.observe(viewLifecycleOwner) {
                val index = it.map { cartProduct -> cartProduct.product.id }.indexOf(product.id)
                if (index != -1 && it[index].product.addToCart > 0) {
                    tvCartQuantity.show()
                    tvCartQuantity.text = root.context.getString(R.string.text_cart_product_quantity, product.addToCart)
                } else {
                    tvCartQuantity.gone()
                }
            }
            Glide.with(root.context).asBitmap().placeholder(R.drawable.ic_category_default)
                .load(product.avatar).into(viewIcon)
            tvName.text = product.name
            tvPrice.text = product.price.toVND()
            if (product.quantity > 0) {
                tvQuantity.text = root.context.getString(R.string.text_product_quantity, product.quantity)
            } else {
                tvQuantity.text = root.context.getString(R.string.text_product_quantity_0)
            }
            if (product.addToCart > 0) {
                tvCartQuantity.show()
                tvCartQuantity.text = root.context.getString(R.string.text_cart_product_quantity, product.addToCart)
            } else tvCartQuantity.gone()
            itemView.setOnClickListener {
                productViewModel.openProductDetail(product)
            }
        }
    }
}