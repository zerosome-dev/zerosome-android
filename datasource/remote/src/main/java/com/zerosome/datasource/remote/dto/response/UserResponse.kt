package com.zerosome.datasource.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("reviewCnt")
    val reviewCnt: Int

)