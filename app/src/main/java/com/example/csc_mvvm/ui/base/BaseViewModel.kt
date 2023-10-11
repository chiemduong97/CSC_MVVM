package com.example.csc_mvvm.ui.base

import androidx.lifecycle.ViewModel
import com.example.csc_mvvm.usecase.errors.ErrorManager

abstract class BaseViewModel : ViewModel() {
    lateinit var errorManager: ErrorManager
}
