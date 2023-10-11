package com.example.csc_mvvm.app

import android.app.Application

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Preferences.createInstance(this)
        Res.onStart(this)
    }
}