package com.example.csc_mvvm.ui.component.main

import android.app.Activity
import android.content.Intent
import com.example.csc_mvvm.R
import com.example.csc_mvvm.databinding.MainActivityBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.ui.component.home.HomeFragment
import com.example.csc_mvvm.utils.ActivityUtils
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var tag = HomeFragment::class.java.name
    private var showOrderCount = false
    private var showOrder = false
    private var showCart = false
    companion object {
        fun newInstance(from: Activity): Intent = Intent(from, MainActivity::class.java)
    }

    private lateinit var binding: MainActivityBinding
    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun bindComponent() {
        showHomeScreen()
    }
    override fun bindEvent() {
        binding.apply {
            navigation.setOnItemSelectedListener { item ->
                if (!item.isChecked) {
                    when (item.itemId) {
                        R.id.menu_home -> showHomeScreen()
                        R.id.menu_wallet -> {}
                        R.id.menu_profile -> {}
                    }

                }
                true
            }
        }
    }

    private fun showHomeScreen() {
        tag = HomeFragment::class.java.simpleName
        ActivityUtils.addFragmentToActivity(supportFragmentManager, HomeFragment(), R.id.frame_layout, tag)
    }

    fun showCart(quantity: Int) {
        showCart = true
        binding.apply {
            tvCartQuantity.text = quantity.toString()
            if (tag != HomeFragment::class.java.simpleName) return
            cvCartPlace.show()
        }
    }

    fun hideCart() {
        showCart = false
        binding.cvCartPlace.gone()
    }

}