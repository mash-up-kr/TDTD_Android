package com.tdtd.data.mapper

import com.tdtd.data.request.MakeRoomRequest
import com.tdtd.data.request.ReplyUserCommentRequest
import com.tdtd.data.response.admin.ModifyRoomNameResponse
import com.tdtd.data.response.room.RoomsResponse
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.entity.ModifyRoomNameEntity
import com.tdtd.domain.entity.ReplyUserCommentWithFileEntity
import com.tdtd.domain.entity.RoomEntity

fun MakeRoomEntity.toNetworkModel() = MakeRoomRequest(
    this.id,
    this.typeEntity
)

fun ReplyUserCommentWithFileEntity.toNetworkModel() = ReplyUserCommentRequest(
    this.nickname,
    this.messageTypeEntity,
    this.textMessage,
    this.voiceFile,
    this.stickerColor,
    this.stickerAngle
)

fun RoomsResponse.toListRoomEntity(): List<RoomEntity> {
    return this.rooms!!.map {
        RoomEntity(
            it.title,
            it.roomCode,
            it.shareUrl,
            it.type,
            it.createdAt,
            it.isBookmark,
            it.isHost
        )
    }
}

fun ModifyRoomNameEntity.toNetworkModel() = ModifyRoomNameResponse(this.roomName)
