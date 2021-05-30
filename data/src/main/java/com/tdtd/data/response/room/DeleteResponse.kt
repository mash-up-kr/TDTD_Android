package com.tdtd.data.response.room

import com.google.gson.annotations.SerializedName
import com.tdtd.data.mapper.toResultEntity
import com.tdtd.domain.entity.DeleteRoomEntity

data class DeleteResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Result?
) {
    fun toEntity() = DeleteRoomEntity(
        code = code,
        message = message,
        result = toResultEntity()
    )
}

class Result