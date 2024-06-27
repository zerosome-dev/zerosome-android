package com.zerosome.domain.model

data class Rollout(
    val id: Int,
    val image: String,
    val categoryD1: String,
    val categoryD2: String,
    val name: String,
    val salesStore: List<String>
)