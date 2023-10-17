package com.example.csc_mvvm.app

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.dto.cart.CartModel
import com.example.csc_mvvm.data.dto.order.OrderLocation
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import com.google.gson.Gson

class Preferences(context: Context) {
    private val sharePreferences: SharedPreferences

    init {
        sharePreferences = context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE)
    }

    var accessToken: String?
        get() = sharePreferences.getString(ACCESS_TOKEN, "")
        set(accessToken) {
            val editor: SharedPreferences.Editor = sharePreferences.edit()
            editor.putString(ACCESS_TOKEN, accessToken)
            editor.apply()
        }

    fun deleteAccessToken() {
        val editor: SharedPreferences.Editor = sharePreferences.edit()
        editor.remove(ACCESS_TOKEN)
        editor.apply()
    }

    var profile: ProfileModel?
        get() {
            val json: String = sharePreferences.getString(PROFILE_MODEL, "").orEmpty()
            if (TextUtils.isEmpty(json)) return null
            val gson = Gson()
            return try {
                gson.fromJson(json, ProfileModel::class.java)
            } catch (e: Exception) {
                null
            }
        }
        set(profile) {
            val editor: SharedPreferences.Editor = sharePreferences.edit()
            val gson = Gson()
            var json = ""
            try {
                json = gson.toJson(profile)
            } catch (ignored: Exception) {
            }
            editor.putString(PROFILE_MODEL, json)
            editor.apply()
        }

    fun deleteProfile() {
        val editor: SharedPreferences.Editor = sharePreferences.edit()
        editor.remove(PROFILE_MODEL)
        editor.apply()
    }
//
//    var deviceToken: String?
//        get() = sharePreferences.getString(DEVICE_TOKEN, "")
//        set(deviceToken) {
//            val editor: SharedPreferences.Editor = sharePreferences.edit()
//            editor.putString(DEVICE_TOKEN, deviceToken)
//            editor.apply()
//        }
//
//    fun deleteDeviceToken() {
//        val editor: SharedPreferences.Editor = sharePreferences.edit()
//        editor.remove(DEVICE_TOKEN)
//        editor.apply()
//    }
//
    var branch: BranchModel?
        get() {
            val json: String = sharePreferences.getString(BRANCH_MODEL, "").orEmpty()
            if (TextUtils.isEmpty(json)) return null
            val gson = Gson()
            return try {
                gson.fromJson(json, BranchModel::class.java)
            } catch (e: Exception) {
                null
            }
        }
        set(branch) {
            val editor: SharedPreferences.Editor = sharePreferences.edit()
            val gson = Gson()
            var json: String? = ""
            try {
                json = gson.toJson(branch)
            } catch (ignored: Exception) {
            }
            editor.putString(BRANCH_MODEL, json)
            editor.apply()
        }
    var cart: CartModel
        get() {
            val json: String = sharePreferences.getString(CART_MODEL, "").orEmpty()
            if (TextUtils.isEmpty(json)) return CartModel()
            val gson = Gson()
            return try {
                gson.fromJson(json, CartModel::class.java)
            } catch (e: Exception) {
                CartModel()
            }
        }
        set(cart) {
            val editor: SharedPreferences.Editor = sharePreferences.edit()
            val gson = Gson()
            var json: String? = ""
            try {
                json = gson.toJson(cart)
            } catch (ignored: Exception) {
            }
            editor.putString(CART_MODEL, json)
            editor.apply()
        }
//    var paymentMethod: PaymentMethod?
//        get() {
//            val json: String = sharePreferences.getString(PAYMENT_METHOD, "")
//            if (TextUtils.isEmpty(json)) return null
//            val gson = Gson()
//            return try {
//                gson.fromJson(json, PaymentMethod::class.java)
//            } catch (e: Exception) {
//                null
//            }
//        }
//        set(paymentMethod) {
//            val editor: SharedPreferences.Editor = sharePreferences.edit()
//            val gson = Gson()
//            var json: String? = ""
//            try {
//                json = gson.toJson(paymentMethod)
//            } catch (ignored: Exception) {
//            }
//            editor.putString(PAYMENT_METHOD, json)
//            editor.apply()
//        }
    var orderLocation: OrderLocation
        get() {
            val json: String = sharePreferences.getString(ORDER_LOCATION, "").orEmpty()
            if (TextUtils.isEmpty(json)) return OrderLocation()
            val gson = Gson()
            return try {
                gson.fromJson(json, OrderLocation::class.java)
            } catch (e: Exception) {
                OrderLocation()
            }
        }
        set(orderLocation) {
            val editor: SharedPreferences.Editor = sharePreferences.edit()
            val gson = Gson()
            var json: String? = ""
            try {
                json = gson.toJson(orderLocation)
            } catch (ignored: Exception) {
            }
            editor.putString(ORDER_LOCATION, json)
            editor.apply()
        }

//    fun deleteOrderLocation() {
//        val editor: SharedPreferences.Editor = sharePreferences.edit()
//        editor.remove(ORDER_LOCATION)
//        editor.apply()
//    }
//
//    fun deleteCart() {
//        val editor: SharedPreferences.Editor = sharePreferences.edit()
//        editor.remove(CART_MODEL)
//        editor.apply()
//    }
//
//    fun deleteAll() {
//        deleteDeviceToken()
//        deleteProfile()
//        deleteAccessToken()
//        deleteCart()
//        deleteOrderLocation()
//    }

    companion object {
        private const val SHARE_PREFERENCES = "SHARE_PREFERENCES"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val DEVICE_TOKEN = "DEVICE_TOKEN"
        private const val PROFILE_MODEL = "PROFILE_MODEL"
        private const val BRANCH_MODEL = "BRANCH_MODEL"
        private const val CART_MODEL = "CART_MODEL"
        private const val PAYMENT_METHOD = "PAYMENT_METHOD"
        private const val ORDER_LOCATION = "ORDER_LOCATION"
        private var preferences: Preferences? = null
        fun newInstance(): Preferences {
            if (preferences == null) {
                throw NullPointerException("Preferences is null!")
            }
            return preferences!!
        }

        @Synchronized
        fun createInstance(context: Context) {
            if (preferences == null) {
                preferences = Preferences(context)
            }
        }
    }
}