package com.tdtd.data.response.room

import com.tdtd.domain.entity.CreatedRoomCodeEntity
import com.tdtd.domain.entity.RoomCodeEntity

data class CreatedRoomCodeResponse(
    var code: Int,
    var message: String,
    var result: RoomCodeResponse
) {
    fun toEntity() = CreatedRoomCodeEntity(
        code = code,
        message = message,
        result = result.toEntity()
    )
}

data class RoomCodeResponse(
    val roomCode: String
) {
    fun toEntity() = RoomCodeEntity(
        roomCode = roomCode
    )
}