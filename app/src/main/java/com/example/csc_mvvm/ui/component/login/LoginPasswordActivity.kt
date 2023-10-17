package com.example.csc_mvvm.ui.component.login

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.csc_mvvm.R
import com.example.csc_mvvm.app.BundleKey
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import com.example.csc_mvvm.databinding.ActivityLoginPasswordBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.ui.base.ViewModelFactory
import com.example.csc_mvvm.ui.component.main.MainActivity
import com.example.csc_mvvm.ui.component.profile.UserViewModel
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show

class LoginPasswordActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun newInstance(from: Activity, email: String): Intent = Intent(from, LoginPasswordActivity::class.java).apply {
            putExtra(BundleKey.EMAIL, email)
        }
    }

    private val email by lazy { intent?.getStringExtra(BundleKey.EMAIL).orEmpty() }

    private val loginViewModel: UserViewModel by viewModels {
        ViewModelFactory()
    }
    private lateinit var binding: ActivityLoginPasswordBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun observeViewModel() {
        loginViewModel.loginPasswordLiveData.observe(this) {
            handleLoginPasswordResult(it)
        }
        loginViewModel.userLiveData.observe(this) {
            handleGetUserResult(it)
        }
        observeErrorMessage(loginViewModel.errorLiveData)
    }

    override fun initViewBinding() {
        binding = ActivityLoginPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun bindEvent() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) finish()
            }
        binding.apply {
            etPassword.run {
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                tbtEye.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                    if (isChecked) {
                        inputType = InputType.TYPE_CLASS_TEXT
                        setSelection(etPassword.text.length)
                    } else {
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        setSelection(etPassword.text.length)
                    }
                }
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        if (s.length < 6) {
                            setButton(false, R.drawable.bg_btn_disable)
                        } else {
                            setButton(true, R.drawable.bg_btn)
                        }
                    }

                    override fun afterTextChanged(s: Editable) {}
                })
            }

            tvLogin.setOnClickListener{
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                currentFocus?.let {
                    imm.hideSoftInputFromWindow(it.windowToken, 0)
                    etPassword.clearFocus()
                }
                loginViewModel.login(email, etPassword.text.toString())
            }
            imvBack.setOnClickListener { finish() }
            tvReset.setOnClickListener {  }
        }
    }

    private fun handleLoginPasswordResult(status: Resource<DataProfileResponse>) {
        when (status) {
            is Resource.Loading -> binding.rllLoading.show()
            is Resource.DataError -> {
                binding.rllLoading.gone()
                status.errorCode?.let { loginViewModel.showError(it) }
            }
            else -> {}
        }
    }

    private fun handleGetUserResult(status: Resource<ProfileModel>) {
        when (status) {
            is Resource.Success -> {
                binding.rllLoading.gone()
                setResult(RESULT_OK)
                finish()
                startActivity(MainActivity.newInstance(this))
            }
            is Resource.DataError -> {
                binding.rllLoading.gone()
                status.errorCode?.let { loginViewModel.showError(it) }
            }
            else -> {}
        }
    }

    fun setButton(enable: Boolean, background: Int) {
        binding.tvLogin.apply {
            isEnabled = enable
            setBackgroundResource(background)
        }
    }

    private fun observeErrorMessage(message: LiveData<String>) {
        message.observe(this) {
            binding.tvError.text = it
            binding.lnlError.visibility = View.VISIBLE
        }
    }
}