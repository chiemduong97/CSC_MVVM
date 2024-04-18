package com.example.csc_mvvm.ui.component.product.navigate

import androidx.fragment.app.Fragment

interface INavigateProduct {
    fun replaceFragment(fragment: Fragment?, tag: String)
    fun addFragment(fragment: Fragment?, tag: String)
    fun popFragment()
}