package com.zerosome.datasource.remote.service

import com.zerosome.datasource.remote.dto.response.UserResponse
import com.zerosome.network.BaseResponse
import com.zerosome.network.BaseService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class UserService @Inject constructor(
    private val client: HttpClient
): BaseService{


    override val apiRoute: String
        get() = "/api/app/v1/member"

    suspend fun getUserDetailData() = client.get(apiRoute).body<BaseResponse<UserResponse>>()

}