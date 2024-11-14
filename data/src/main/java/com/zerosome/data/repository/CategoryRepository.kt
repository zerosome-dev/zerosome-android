package com.zerosome.data.repository

import com.zerosome.core.constants.ClientError
import com.zerosome.core.constants.ClientExceptions
import com.zerosome.data.apiCall
import com.zerosome.data.mapper.domainModel
import com.zerosome.datasource.remote.service.CategoryService
import com.zerosome.datasource.remote.service.FilterService
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.repository.CategoryRepository
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val filterService: FilterService,
) : CategoryRepository {
    override suspend fun getAllCategories(): List<CategoryDepth1> = apiCall(
        block = { categoryService.getAllCategories() },
        transform = {
            it?.map { data -> data.domainModel }
                ?: throw ClientExceptions(ClientError.RESPONSE_NOT_VALIDATE_EXCEPTION)
        }
    )

    override suspend fun getCategoryDepth2(categoryDepth1: String): List<CategoryDepth2> = apiCall(
        block = { filterService.getDepth2CategoryById(categoryDepth1) },
        transform = {
            it?.map { data -> data.domainModel } ?: emptyList()
        }
    )
}