package com.tdtd.domain.entity

data class ReplyUserCommentWithUrlEntity(
    val nickname: String,
    val messageType: MakeRoomType,
    val textMessage: String?,
    val voiceUrl: String?,
    val stickerColor: String,     //todo: sehee Enum Type 으로 변경해야함
    val stickerAngle: Int
)