package com.example.csc_mvvm.data.local

import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.dto.order.OrderLocation
import com.example.csc_mvvm.data.dto.profile.ProfileModel

class LocalData(private val preferences: Preferences) {
    fun getUser() : Resource<ProfileModel> {
        return preferences.profile?.let {
            Resource.Success(it)
        } ?: Resource.DataError(1001)
    }

    fun getBranch() : Resource<BranchModel> {
        return preferences.branch?.let {
            Resource.Success(it)
        } ?: Resource.DataError(1001)
    }

    fun getOrderLocation() : Resource<OrderLocation> {
        return Resource.Success(preferences.orderLocation)
    }
}