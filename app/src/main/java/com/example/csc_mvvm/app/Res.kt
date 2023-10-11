package com.example.csc_mvvm.app

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object Res {
    lateinit var context: Context

    @JvmStatic
    fun onStart(context: Context) {
        this.context = context.applicationContext
    }
}