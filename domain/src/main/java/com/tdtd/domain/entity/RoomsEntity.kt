package com.tdtd.domain.entity

data class RoomsEntity(
    var code: Int,
    var message: String,
    var rooms: List<RoomEntity>?
)