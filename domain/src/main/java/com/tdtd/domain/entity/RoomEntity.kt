package com.tdtd.domain.entity

sealed class RoomEntity {
    data class Room(
            var title: String,
            var room_code: String,
            var share_url: String,
            var created_at: String,
            var is_bookmark: Boolean,
            var is_host: Boolean
    ) : RoomEntity()
}