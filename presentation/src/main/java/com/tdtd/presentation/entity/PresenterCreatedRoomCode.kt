package com.tdtd.presentation.entity

import com.tdtd.domain.entity.RoomCodeEntity

data class PresenterCreatedRoomCode(
    var code: Int,
    var message: String,
    var result: RoomCodeEntity
)