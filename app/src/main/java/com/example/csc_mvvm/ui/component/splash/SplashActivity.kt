package com.example.csc_mvvm.ui.component.splash

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.databinding.ActivitySplashBinding
import com.example.csc_mvvm.ui.base.BaseActivity
import com.example.csc_mvvm.ui.component.login.LoginEmailActivity
import com.example.csc_mvvm.ui.component.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun observeViewModel() {}

    override fun initViewBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun bindEvent() {
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            val preferences = Preferences.newInstance()
            if (preferences.accessToken.isNullOrEmpty() || preferences.profile == null) {
                startActivity(LoginEmailActivity.newInstance(this))
            } else {
                startActivity(MainActivity.newInstance(this))
            }
        }, 3000)
    }

}