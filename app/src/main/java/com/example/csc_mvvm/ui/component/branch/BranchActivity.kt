package com.example.csc_mvvm.ui.component.branch

import android.app.Activity
import android.content.Intent
import com.example.csc_mvvm.R
import com.example.csc_mvvm.databinding.ActivityEmptyBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.utils.ActivityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BranchActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun newInstance(from: Activity): Intent = Intent(from, BranchActivity::class.java)
    }

    private lateinit var binding: ActivityEmptyBinding
    override fun observeViewModel() {
    }

    override fun initViewBinding() {
        binding = ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun bindComponent() {
        ActivityUtils.addFragmentToActivity(
            supportFragmentManager,
            BranchFragment(),
            R.id.container,
            BranchFragment::class.java.simpleName
        )
    }
}