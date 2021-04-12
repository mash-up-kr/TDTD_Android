package com.tdtd.domain.entity

sealed class RoomsEntity {
    data class Rooms(
            var code: Int,
            var message: String,
            var rooms: List<RoomEntity>
    ) : RoomsEntity()
}