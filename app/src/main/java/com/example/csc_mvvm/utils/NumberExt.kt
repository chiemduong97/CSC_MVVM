package com.example.csc_mvvm.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.toVND(): String {
    return NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(this)
}