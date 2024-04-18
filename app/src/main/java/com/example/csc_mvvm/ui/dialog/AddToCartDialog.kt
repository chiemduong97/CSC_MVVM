package com.example.csc_mvvm.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.csc_mvvm.R
import com.example.csc_mvvm.data.dto.cart.CartProductModel
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.databinding.DialogAddToCartBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.NumberFormat
import java.util.Locale

class AddToCartDialog(
    private val product: ProductModel,
    private val addToCart: (cart : CartProductModel) -> Unit,
    private val showDetail: (product: ProductModel) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogAddToCartBinding
    private var quantity = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogAddToCartBinding.inflate(inflater)
        dialog?.run {
            setOnShowListener {
                findViewById<View>(com.google.android.material.R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent)
            }
            window?.apply {
                requestFeature(Window.FEATURE_NO_TITLE)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
        binding.apply {
            context?.run {
                Glide.with(this).asBitmap().placeholder(R.drawable.ic_category_default).load(product.avatar).into(imvProductAvatar)
            }
            tvName.text = product.name
            tvPrice.text = NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(product.price)
            tvDescription.text = product.description
            tvQuantity.text = quantity.toString()
            btnMinus.isEnabled = quantity > 1
            imvBack.setOnClickListener { dismiss() }
            btnMinus.setOnClickListener {
                quantity--
                updateQuantity()
            }
            btnPlus.setOnClickListener {
                quantity++
                updateQuantity()
            }
            tvAddToCart.setOnClickListener {
                addToCart.invoke(CartProductModel(product, quantity).apply {
                    product.addToCart += quantity
                })
                dismiss()
            }
            rltProduct.setOnClickListener {
                dismiss()
                showDetail.invoke(product)
            }
        }
        return binding.root
    }

    private fun updateQuantity() {
        binding.apply {
            btnMinus.isEnabled = quantity > 1
            tvQuantity.text = quantity.toString()
        }
    }

    fun show(fragmentManager: FragmentManager?) {
        fragmentManager ?: return
        show(fragmentManager, tag)
    }
}