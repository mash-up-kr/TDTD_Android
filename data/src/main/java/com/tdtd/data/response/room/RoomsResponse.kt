package com.tdtd.data.response.room

import com.tdtd.domain.entity.RoomsEntity

/**
 * RoomsEntity
 * @param code
 * @param message
 * @param rooms
 */
data class RoomsResponse(
    var code: Int,
    var message: String,
    var rooms: List<RoomResponse>?
) {
    fun toEntity() = RoomsEntity(
        code = code,
        message = message,
        rooms = rooms?.map {
            it.toEntity()
        }
    )
}