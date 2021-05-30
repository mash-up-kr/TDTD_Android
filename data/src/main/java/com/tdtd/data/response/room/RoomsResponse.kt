package com.tdtd.data.response.room

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.RoomsEntity

/**
 * RoomsEntity
 * @param code
 * @param message
 * @param rooms
 */
data class RoomsResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
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