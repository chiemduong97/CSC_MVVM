package com.example.client.models.profile

import com.example.csc_mvvm.base.BaseModel
import com.google.gson.annotations.SerializedName

data class ProfileModel(
    val id: Int = -1,
    val email: String = "",
    var avatar: String = "",
    var fullname: String = "",
    var birthday: String = "",
    var phone: String = "",
    var cscPoint: Double = 0.0,
    var wallet: Double = 0.0
) : BaseModel()

data class ProfileResponse(
    val id: Int?,
    val email: String?,
    var avatar: String?,
    var fullname: String?,
    var birthday: String?,
    var phone: String?,
    var wallet: Double?,
    @SerializedName("csc_point")
    var cscPoint: Double?
) : BaseModel() {
    fun toProfileModel() = ProfileModel(
        id = id ?: -1,
        email = email.orEmpty(),
        avatar = avatar.orEmpty(),
        fullname = fullname.orEmpty(),
        birthday = birthday.orEmpty(),
        phone = phone.orEmpty(),
        cscPoint = cscPoint ?: 0.0,
        wallet = wallet ?: 0.0
    )
}

data class DataProfileResponse(
    @SerializedName("access_token")
    val accessToken: String
)

data class ProfileRequest(
    var email: String? = null,
    var avatar: String? = null,
    var fullname: String? = null,
    var birthday: String? = null,
    var phone: String? = null,
    var newPassword: String? = null,
    var oldPassword: String? = null,
)