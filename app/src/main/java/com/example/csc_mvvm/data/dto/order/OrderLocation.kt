package com.example.csc_mvvm.data.dto.order

import com.example.csc_mvvm.base.BaseModel


class OrderLocation(
        var lat: Double = 10.8009046,
        var lng: Double = 106.8080558,
        var address: String = "45/23 Đường Số 3, Long Trường, Quận 9, Thành phố Hồ Chí Minh"
) : BaseModel()
