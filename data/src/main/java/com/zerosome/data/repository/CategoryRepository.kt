package com.zerosome.data.repository

import com.zerosome.data.mapper.domainModel
import com.zerosome.data.mapper.mapToDomain
import com.zerosome.datasource.local.source.TokenSource
import com.zerosome.datasource.remote.service.CategoryService
import com.zerosome.datasource.remote.service.FilterService
import com.zerosome.design.R
import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.domain.repository.CategoryRepository
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val filterService: FilterService,
) : CategoryRepository {
    override fun getAllCategories(): Flow<NetworkResult<List<CategoryDepth1>>> = flow {
        emit(NetworkResult.Loading)
        delay(3000)
        emit(NetworkResult.Success(categorySet))
    }

//    override fun getAllCategories(): Flow<NetworkResult<List<CategoryDepth1>>> = categoryService.getAllCategories(
//    ).mapToDomain { it.map { category -> category.domainModel } }

    override fun getCategoryDepth2(categoryDepth1: String): Flow<NetworkResult<List<CategoryDepth2>>> =
        filterService.getDepth2CategoryById(
            categoryDepth1
        ).mapToDomain { it.map { category -> category.domainModel } }
}

private val categorySet = listOf(
    CategoryDepth1(
        "001", "카페음료",
        listOf(
            CategoryDepth2(
                "101", "스타벅스", categoryPainterResId = R.drawable.img_starbucks,
            ),
            CategoryDepth2(
                "102", "메가커피", categoryPainterResId = R.drawable.img_mgc,
            ),
            CategoryDepth2(
                "103", "빽다방", categoryPainterResId = R.drawable.img_bback,
            ),
            CategoryDepth2(
                "104", "투썸플레이스", categoryPainterResId = R.drawable.img_twosome,
            ),
        ),
    ),
    CategoryDepth1(
        "002", "탄산수/음료", listOf(
            CategoryDepth2("201", "탄산수", categoryPainterResId = R.drawable.img_sparkwater),
            CategoryDepth2("202", "탄산음료", categoryPainterResId = R.drawable.img_sparkjuice),
            CategoryDepth2("203", "커피음료", categoryPainterResId = R.drawable.img_coffee),
            CategoryDepth2("204", "차음료", categoryPainterResId = R.drawable.img_tea),
            CategoryDepth2("205", "어린이음료", categoryPainterResId = R.drawable.img_baby),
            CategoryDepth2("206", "무알콜음료", categoryPainterResId = R.drawable.img_alcohol),
            CategoryDepth2("207", "스포츠음료", categoryPainterResId = R.drawable.img_sport),
            CategoryDepth2("208", "숙취/건감음료", categoryPainterResId = R.drawable.img_health),
            )
    ),
    CategoryDepth1(
        "003", "과자/아이스크림", listOf(
            CategoryDepth2("301", "과자", categoryPainterResId = R.drawable.img_snack),
            CategoryDepth2("302", "아이스크림", categoryPainterResId = R.drawable.img_icecream),
        )
    )
)