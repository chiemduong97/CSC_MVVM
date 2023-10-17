package com.example.csc_mvvm.ui.component.main

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.example.csc_mvvm.R
import com.example.csc_mvvm.databinding.MainActivityBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.ui.base.ViewModelFactory
import com.example.csc_mvvm.ui.component.home.HomeFragment
import com.example.csc_mvvm.ui.component.order.OrderViewModel
import com.example.csc_mvvm.ui.component.profile.UserViewModel
import com.example.csc_mvvm.utils.ActivityUtils

class MainActivity : BaseActivity() {
    private var tag = HomeFragment::class.java.name
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
}