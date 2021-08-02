package com.tdtd.data.response.room

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.DeleteRoomEntity

data class DeleteResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Unit
) {
    fun toEntity() = DeleteRoomEntity(
        code = code,
        message = message,
        result = Unit
    )
}