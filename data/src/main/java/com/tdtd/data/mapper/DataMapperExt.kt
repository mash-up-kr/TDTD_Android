package com.tdtd.data.mapper

import com.tdtd.data.request.MakeRoomRequest
import com.tdtd.data.request.ReplyUserCommentRequest
import com.tdtd.data.response.room.RoomsResponse
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.entity.ReplyUserCommentWithFileEntity
import com.tdtd.domain.entity.ResultEntity
import com.tdtd.domain.entity.RoomEntity

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

fun RoomsResponse.toListRoomEntity(): List<RoomEntity> {
    return this.rooms!!.map {
        RoomEntity(it.title, it.roomCode, it.shareUrl, it.createdAt, it.isBookmark, it.isHost)
    }
}

fun toResultEntity() = ResultEntity(

)
