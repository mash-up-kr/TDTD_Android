package com.tdtd.domain.entity

data class DeleteRoomEntity(
    val code: Int,
    val message: String,
    val result: ResultEntity
)

class ResultEntity