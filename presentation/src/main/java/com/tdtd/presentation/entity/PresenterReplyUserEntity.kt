package com.tdtd.presentation.entity

import com.tdtd.domain.entity.RoomTypeEntity

data class PresenterReplyUserEntity(
    var code: Int,
    var message: String,
    var result: PresenterReplyUserCommentWithUrl
)

data class PresenterReplyUserCommentWithUrl(
    val nickname: String,
    val messageTypeEntity: RoomTypeEntity,
    val textMessage: String?,
    val voiceUrl: String?,
    val stickerColor: PresenterStickerColorType,
    val stickerAngle: Int
)