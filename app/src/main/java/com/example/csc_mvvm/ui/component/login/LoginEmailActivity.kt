package com.example.csc_mvvm.ui.component.login

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.csc_mvvm.R
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.databinding.ActivityLoginEmailBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.ui.component.profile.UserViewModel
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginEmailActivity : BaseActivity() {

    companion object {
        fun newInstance(from: Activity): Intent = Intent(from, LoginEmailActivity::class.java)
    }

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun observeViewModel() {
        userViewModel.loginEmailLiveData.observe(this) {
            handleLoginEmailResult(it)
        }
        observeErrorMessage(userViewModel.errorLiveData)
    }

    override fun initViewBinding() {
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun bindEvent() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) finish()
            }
        binding.apply {
            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isEmpty()) {
                        setButton(false, R.drawable.bg_btn_disable)
                    } else {
                        val regex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$".toRegex()
                        if (etEmail.text.toString().matches(regex)) {
                            setButton(true, R.drawable.bg_btn)
                        } else {
                            setButton(false, R.drawable.bg_btn_disable)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable) {}
            })
            tvNext.setOnClickListener {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                currentFocus?.let {
                    imm.hideSoftInputFromWindow(it.windowToken, 0)
                    etEmail.clearFocus()
                }
                userViewModel.checkEmail(etEmail.text.toString())
            }
            tvRegister.setOnClickListener {

            }
        }

    }

    private fun setButton(enable: Boolean, background: Int) {
        binding.tvNext.apply {
            isEnabled = enable
            setBackgroundResource(background)
        }
    }

    private fun handleLoginEmailResult(status: Resource<Any>) {
        when (status) {
            is Resource.Loading -> binding.rllLoading.show()
            is Resource.Success -> {
                binding.rllLoading.gone()
                navigateToLoginPasswordScreen()
            }

            is Resource.DataError -> {
                binding.rllLoading.gone()
                status.errorCode?.let { userViewModel.showError(it) }
            }
        }
    }

    private fun navigateToLoginPasswordScreen() {
        activityResultLauncher.launch(LoginPasswordActivity.newInstance(this, binding.etEmail.text.toString()))
    }

    private fun observeErrorMessage(message: LiveData<String>) {
        message.observe(this) {
            binding.tvError.text = it
            binding.lnlError.visibility = View.VISIBLE
        }
    }
}