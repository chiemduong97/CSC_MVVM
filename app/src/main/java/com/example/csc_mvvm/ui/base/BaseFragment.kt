package com.example.csc_mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment : Fragment() {

    private var _binding: ViewBinding? = null
    open val bindingInflater: ((LayoutInflater, ViewGroup?, Boolean) -> ViewBinding)? = null
    @Suppress("UNCHECKED_CAST")
    protected fun <T: ViewBinding> binding() : T {
        return _binding as T
    }
    protected fun setBinding(binding: ViewBinding?) {
        _binding = binding
    }
    abstract fun observeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeViewModel()
        setBinding(bindingInflater?.invoke(inflater, container, false))
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        bindComponent()
        bindEvent()
        bindData()
    }

    protected fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    protected open fun initData() {}
    protected open fun bindData() {}
    protected open fun bindComponent() {}
    protected open fun bindEvent() {}
}