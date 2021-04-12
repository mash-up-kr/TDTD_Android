package com.tdtd.data.entity

import com.google.gson.annotations.SerializedName

/**
 * RoomsEntity
 * @param code
 * @param message
 * @param rooms
 */
data class RoomsEntity(
        @SerializedName("code")
        var code: Int,
        @SerializedName(value = "message")
        var message: String,
        @SerializedName("rooms")
        var rooms: List<RoomEntity>
)