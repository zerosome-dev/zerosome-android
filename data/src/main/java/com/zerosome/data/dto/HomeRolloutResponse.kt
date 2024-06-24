package com.zerosome.data.dto

import kotlinx.serialization.SerialName

data class HomeRolloutResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("d1Category")
    val categoryDepth1: String,
    @SerialName("d2Category")
    val categoryDepth2: String,
    @SerialName("name")
    val name: String,
    @SerialName("salesStore")
    val salesStore: List<String>
)