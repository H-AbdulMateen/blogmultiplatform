package com.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class User(
    @SerialName("_id")
    actual val _id: String = "",
    actual val username: String = "",
    actual val password: String = ""
)
@Serializable
actual data class UserWithoutPassword(
    @SerialName("_id")
    actual val _id: String = "",
    actual val username: String = ""
)