package com.example.csc_mvvm.ui.component.order

import com.example.csc_mvvm.ui.base.BaseViewModel
import com.example.csc_mvvm.usecase.order.OrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderUseCase: OrderUseCase) : BaseViewModel() {

}