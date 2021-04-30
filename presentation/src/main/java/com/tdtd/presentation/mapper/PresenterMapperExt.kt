package com.tdtd.presentation.mapper

import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.entity.RoomCodeEntity
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.presentation.entity.MakeRoom
import com.tdtd.presentation.entity.Room

fun List<RoomEntity>.toPresenterRoom(): List<Room> {
    return this.map {
        Room(it.title, it.roomCode, it.shareUrl, it.createdAt, it.isBookmark, it.isHost)
    }
}

fun RoomCodeEntity.toMakeRoom(): MakeRoom {
    return this.toMakeRoom()
}

fun MakeRoom.toRoomCodeEntity(): MakeRoomEntity {
    return this.toRoomCodeEntity()
}