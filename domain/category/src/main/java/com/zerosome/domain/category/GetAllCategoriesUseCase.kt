package com.zerosome.domain.category

import com.zerosome.domain.model.CategoryDepth1
import com.zerosome.domain.model.CategoryDepth2
import com.zerosome.network.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(

){

    operator fun invoke(): Flow<NetworkResult<List<CategoryDepth1>>> = flow {
        emit(NetworkResult.Loading)
        delay(500)
        emit(
            NetworkResult.Success(
                listOf(
                    CategoryDepth1(
                        "01", "생수/음료", categoryDepth2 = listOf(
                            CategoryDepth2(categoryCode = "001", "탄산수"),
                            CategoryDepth2(categoryCode = "002", "탄산음료"),
                            CategoryDepth2(categoryCode = "003", "커피음료"),
                            CategoryDepth2(categoryCode = "004", "차음료"),
                            CategoryDepth2(categoryCode = "005", "어린이음료"),
                            CategoryDepth2(categoryCode = "006", "무알콜음료"),
                            CategoryDepth2(categoryCode = "007", "스포츠음료"),
                            CategoryDepth2(categoryCode = "008", "숙취/건강음료"),
                        )
                    ),
                    CategoryDepth1(
                        "02", "과자/아이스크림", categoryDepth2 = listOf(
                            CategoryDepth2("009", "과자"),
                            CategoryDepth2("010", "아이스크림")
                        )
                    )
                )
            )

        )
    }
}