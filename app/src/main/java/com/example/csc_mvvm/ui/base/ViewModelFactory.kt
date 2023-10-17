package com.example.csc_mvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.csc_mvvm.ui.component.branch.BranchViewModel
import com.example.csc_mvvm.ui.component.category.CategoryViewModel
import com.example.csc_mvvm.ui.component.profile.UserViewModel
import com.example.csc_mvvm.ui.component.order.OrderViewModel
import com.example.csc_mvvm.ui.component.product.ProductViewModel
import com.example.csc_mvvm.usecase.BranchUseCase
import com.example.csc_mvvm.usecase.CategoryUseCase
import com.example.csc_mvvm.usecase.OrderUseCase
import com.example.csc_mvvm.usecase.ProductUseCase
import com.example.csc_mvvm.usecase.UserUseCase

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(UserViewModel::class.java) -> UserViewModel(UserUseCase())
            isAssignableFrom(OrderViewModel::class.java) -> OrderViewModel(OrderUseCase())
            isAssignableFrom(CategoryViewModel::class.java) -> CategoryViewModel(CategoryUseCase())
            isAssignableFrom(BranchViewModel::class.java) -> BranchViewModel(BranchUseCase())
            isAssignableFrom(ProductViewModel::class.java) -> ProductViewModel(ProductUseCase())
            else -> throw IllegalArgumentException("Unknown class name")
        }
    } as T
}