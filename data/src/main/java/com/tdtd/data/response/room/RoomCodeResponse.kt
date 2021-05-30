package com.tdtd.data.response.room

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.CreatedRoomCodeEntity
import com.tdtd.domain.entity.RoomCodeEntity

data class CreatedRoomCodeResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: RoomCodeResponse
) {
    fun toEntity() = CreatedRoomCodeEntity(
        code = code,
        message = message,
        result = result.toEntity()
    )
}

data class RoomCodeResponse(
    @SerializedName("room_code")
    val roomCode: String
) {
    fun toEntity() = RoomCodeEntity(
        roomCode = roomCode
    )
}