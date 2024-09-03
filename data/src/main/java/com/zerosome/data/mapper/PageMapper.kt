package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.PagedResponse
import com.zerosome.domain.model.Page

fun<T> PagedResponse<T>.getAsDomainModel(): Page<T> = Page(page = content, offset, limit)
