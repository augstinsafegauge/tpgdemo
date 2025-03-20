package com.example.tpgdemo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatsPhoto(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val imageUrl: String, // JSON field is "url"
)
