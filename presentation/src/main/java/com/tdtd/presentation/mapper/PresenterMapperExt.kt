package com.tdtd.presentation.mapper

import com.tdtd.domain.entity.*
import com.tdtd.presentation.entity.*

fun List<RoomEntity>.toPresenterRoom(): List<Room> {
    return this.map {
        Room(it.title, it.roomCode, it.shareUrl, it.createdAt, it.isBookmark, it.isHost)
    }
}

fun CreatedRoomCodeEntity.toPresenterCreated() = PresenterCreatedRoomCode(
    code = this.code,
    message = this.message,
    result = this.result
)

fun DeleteRoomEntity.toPresenterDeleteRoom() = PresenterDeleteRoom(
    code = this.code,
    message = this.message,
    result = this.result
)

fun RoomDetailEntity.toPresenterRoomDetailEntity() = PresenterRoomDetailEntity(
    code = this.code,
    message = this.message,
    result = this.result.toPresenterResultRoomInfoEntity()
)

fun ResultRoomInfoEntity.toPresenterResultRoomInfoEntity() = PresenterResultRoomInfoEntity(
    title = this.title,
    type = this.type,
    shareUrl = this.shareUrl,
    comments = this.comments.toComments()
)

fun List<UserDetailCommentEntity>.toComments(): List<Comments> {
    return this.map {
        Comments(
            it.id,
            it.isMine,
            it.nickname,
            it.text,
            it.voiceFileUrl,
            it.stickerColor.toPresenterStickerColorType(),
            it.stickerAngle,
            it.createAt,
        )
    }
}

fun StickerColorType.toPresenterStickerColorType() = PresenterStickerColorType.values()[ordinal]