package com.iftikar.mediuser.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val address: String,
    val block: Int,
    @SerializedName("date_of_account_creation")
    val dateOfAccountCreation: String,
    val email: String,
    val id: Int,
    @SerializedName("isApproved")
    val isApproved: Int,
    val name: String,
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("pin_code")
    val pinCode: String,
    @SerializedName("user_id")
    val userId: String
)