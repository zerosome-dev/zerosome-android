package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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
    val salesStore: List<String?>? = null
)