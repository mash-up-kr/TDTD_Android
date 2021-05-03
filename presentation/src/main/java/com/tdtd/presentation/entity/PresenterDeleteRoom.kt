package com.tdtd.presentation.entity

import com.tdtd.domain.entity.ResultEntity

data class PresenterDeleteRoom(
    val code: Int,
    val message: String,
    val result: ResultEntity?
)