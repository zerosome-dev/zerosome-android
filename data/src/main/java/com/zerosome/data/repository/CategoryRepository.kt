package com.zerosome.data.repository

import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.remote.service.CategoryService
import com.zerosome.datasource.remote.service.FilterService
import com.zerosome.design.R
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.network.NetworkResult
import com.zerosome.network.safeCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val filterService: FilterService,
) : CategoryRepository {
    override fun getAllCategories(): Flow<NetworkResult<List<CategoryDepth1>>> = safeCall {
        categoryService.getAllCategories()
    }.mapToDomain { it.map { category -> category.domainModel } }

    override fun getCategoryDepth2(categoryDepth1: String): Flow<NetworkResult<List<CategoryDepth2>>> = safeCall {
        filterService.getDepth2CategoryById(
            categoryDepth1
        )
    }.mapToDomain { it.map { category -> category.domainModel } }

}