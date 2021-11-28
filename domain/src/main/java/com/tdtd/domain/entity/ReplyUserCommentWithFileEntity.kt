package com.tdtd.domain.entity

data class ReplyUserCommentWithFileEntity(
    val nickname: String,
    val messageTypeEntity: RoomTypeEntity,
    val textMessage: String?,
    val voiceFile: String?,
    val stickerColor: StickerColorType,
    val stickerAngle: Int
)