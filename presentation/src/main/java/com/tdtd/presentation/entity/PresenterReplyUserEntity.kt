package com.tdtd.presentation.entity

import com.tdtd.domain.entity.MakeRoomType

data class PresenterReplyUserEntity(
    var code: Int,
    var message: String,
    var result: PresenterReplyUserCommentWithUrl
)

data class PresenterReplyUserCommentWithUrl(
    val nickname: String,
    val messageType: MakeRoomType,
    val textMessage: String?,
    val voiceUrl: String?,
    val stickerColor: PresenterStickerColorType,
    val stickerAngle: Int
)