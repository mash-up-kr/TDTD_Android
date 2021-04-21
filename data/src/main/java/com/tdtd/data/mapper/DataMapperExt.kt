package com.tdtd.data.mapper

import com.tdtd.data.request.MakeRoomRequest
import com.tdtd.data.request.ReplyUserCommentRequest
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.entity.ReplyUserCommentWithFileEntity

fun MakeRoomEntity.toNetworkModel() = MakeRoomRequest(
    this.id,
    this.type
)

fun ReplyUserCommentWithFileEntity.toNetworkModel() = ReplyUserCommentRequest(
    this.nickname,
    this.messageType,
    this.textMessage,
    this.voiceFile,
    this.stickerColor,
    this.stickerAngle
)