package com.tdtd.domain.entity

data class RoomUrlEntity(
    var code: Int,
    var message: String,
    var result: SharedUrlEntity
)

data class SharedUrlEntity(
    val shareUrl: String
)