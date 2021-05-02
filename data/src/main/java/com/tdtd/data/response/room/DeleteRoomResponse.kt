package com.tdtd.data.response.room

import com.tdtd.data.mapper.toResultEntity
import com.tdtd.domain.entity.DeleteRoomEntity

data class DeleteResponse(
    val code: Int,
    val message: String,
    val result: Result
) {
    fun toEntity() = DeleteRoomEntity(
        code = code,
        message= message,
        result = result.toResultEntity()
    )
}

class Result