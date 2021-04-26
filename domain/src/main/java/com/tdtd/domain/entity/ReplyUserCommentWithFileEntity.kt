package com.tdtd.domain.entity

data class ReplyUserCommentWithFileEntity(
    val nickname: String,
    val messageType: MakeRoomType,
    val textMessage: String?,
    val voiceFile: String?,
    val stickerColor: StickerColorType,
    val stickerAngle: Int
)