package com.tdtd.domain.entity

data class ReplyUserCommentWithUrlEntity(
    val nickname: String,
    val messageTypeEntity: RoomTypeEntity,
    val textMessage: String?,
    val voiceUrl: String?,
    val stickerColor: StickerColorType,
    val stickerAngle: Int
)