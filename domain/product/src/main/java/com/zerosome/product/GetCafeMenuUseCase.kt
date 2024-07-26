package com.zerosome.product

import android.util.Log
import com.zerosome.domain.repository.HomeRepository
import javax.inject.Inject

class GetCafeMenuUseCase @Inject constructor(
    private val homeRepository: HomeRepository
){

    operator fun invoke() = homeRepository.getCafe()
}