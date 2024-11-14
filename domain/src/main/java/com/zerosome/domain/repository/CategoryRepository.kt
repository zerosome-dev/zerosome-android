package com.zerosome.domain.repository

import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2

interface CategoryRepository {

    suspend fun getAllCategories(): List<CategoryDepth1>

    suspend fun getCategoryDepth2(categoryDepth1: String): List<CategoryDepth2>
}