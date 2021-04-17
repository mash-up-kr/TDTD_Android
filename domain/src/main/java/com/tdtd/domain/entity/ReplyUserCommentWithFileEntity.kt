package com.tdtd.domain.entity

data class ReplyUserCommentWithFileEntity(
    val nickname: String,
    val messageType: MakeRoomType,
    val textMessage: String?,
    val voiceFile: String?,
    val stickerColor: String,     //todo: sehee Enum Type 으로 변경해야함
    val stickerAngle: Int
)