package com.zerosome.domain.repository

import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<NetworkResult<List<CategoryDepth1>>>

    fun getCategoryDepth2(categoryDepth1: String): Flow<NetworkResult<List<CategoryDepth2>>>
}