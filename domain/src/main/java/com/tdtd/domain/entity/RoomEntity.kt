package com.tdtd.domain.entity

data class RoomEntity(
    var title: String,
    var roomCode: String,
    var shareUrl: String,
    var type: String,
    var createdAt: String,
    var isBookmark: Boolean,
    var isHost: Boolean
)