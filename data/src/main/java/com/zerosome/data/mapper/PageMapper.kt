package com.zerosome.data.mapper

import com.zerosome.datasource.remote.dto.response.PagedResponse
import com.zerosome.domain.model.Page

fun<T, R> PagedResponse<T>.getAsDomainModel(contentTransformer: (T) -> R): Page<R> = Page(page = content.map(contentTransformer), offset, limit)
