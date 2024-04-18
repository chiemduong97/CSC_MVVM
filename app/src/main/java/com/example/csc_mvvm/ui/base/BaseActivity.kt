package com.example.csc_mvvm.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun observeViewModel()
    abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        observeViewModel()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initData()
        bindComponent()
        bindEvent()
        bindData()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initData()
        bindComponent()
        bindEvent()
        bindData()
    }

    override fun onResume() {
        super.onResume()
        getCart()
    }

    protected open fun initData() {}
    protected open fun bindData() {}
    protected open fun bindComponent() {}
    protected open fun bindEvent() {}

    protected open fun getCart() {}

}
