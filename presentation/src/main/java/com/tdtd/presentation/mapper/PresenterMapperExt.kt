package com.tdtd.presentation.mapper

import com.tdtd.domain.entity.*
import com.tdtd.domain.entity.StickerColorType
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

fun RoomUrlEntity.toPresenterRoomUrlEntity() = PresenterRoomUrlEntity(
    code = this.code,
    message = this.message,
    result = this.result.toPresenterSharedUrlEntity()
)

fun SharedUrlEntity.toPresenterSharedUrlEntity() = PresenterSharedUrlEntity(
    shareUrl = this.shareUrl
)


fun DeleteRoomEntity.toPresenterDeleteRoom() = PresenterDeleteRoom(
    code = this.code,
    message = this.message,
    result = this.result
)

fun RoomsEntity.toRooms() = Rooms(
    code = this.code,
    message = this.message,
    rooms = this.rooms!!.toPresenterRoom()
)

fun RoomEntity.toRoom() = Room(
    title = this.title,
    room_code = this.roomCode,
    created_at = this.createdAt,
    is_host = this.isHost,
    is_bookmark = this.isBookmark,
    share_url = this.shareUrl
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

fun ReplyUserEntity.toPresenterReplyUserEntity() = PresenterReplyUserEntity(
    code = this.code,
    message = this.message,
    result = this.result.toPresenterReplyUserCommentWithUrl()
)

fun ReplyUserCommentWithUrlEntity.toPresenterReplyUserCommentWithUrl() =
    PresenterReplyUserCommentWithUrl(
        nickname = this.nickname,
        messageType = this.messageType,
        textMessage = this.textMessage,
        voiceUrl = this.voiceUrl,
        stickerColor = this.stickerColor.toPresenterStickerColorType(),
        stickerAngle = this.stickerAngle
    )