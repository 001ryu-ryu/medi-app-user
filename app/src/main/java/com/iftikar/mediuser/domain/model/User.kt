package com.iftikar.mediuser.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val address: String,
    val block: Int,
    @SerialName("date_of_account_creation")
    val dateOfAccountCreation: String,
    val email: String,
    val id: Int,
    val isApproved: Int,
    val name: String,
    val password: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("pin_code")
    val pinCode: String,
    @SerialName("user_id")
    val userId: String
)