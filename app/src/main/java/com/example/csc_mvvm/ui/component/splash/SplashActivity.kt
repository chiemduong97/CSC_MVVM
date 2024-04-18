package com.example.csc_mvvm.ui.component.splash

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.csc_mvvm.databinding.ActivitySplashBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.ui.component.login.LoginEmailActivity
import com.example.csc_mvvm.ui.component.main.MainActivity
import com.example.csc_mvvm.ui.component.profile.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val userViewModel: UserViewModel by viewModels()
    override fun observeViewModel() {}

    override fun initViewBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun bindEvent() {
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            if (userViewModel.isLogin()) {
                startActivity(MainActivity.newInstance(this))
            } else {
                startActivity(LoginEmailActivity.newInstance(this))

            }
        }, 3000)
    }

}