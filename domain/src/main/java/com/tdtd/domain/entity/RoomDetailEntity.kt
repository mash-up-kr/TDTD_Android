package com.tdtd.domain.entity

data class RoomDetailEntity(
    val code: String,
    val message: String,
    val result: ResultRoomInfoEntity
)

data class ResultRoomInfoEntity(
    val title: String,
    val type: MakeRoomType,
    val shareUrl: String,
    val comments: List<UserCommentEntity>
)

data class UserCommentEntity(
    val id: String,
    val nickname: String,
    val text: String? = null,
    val voiceFileUrl: String? = null,
    val stickerColor: String,
    val stickerAngle: Long,
    val createAt: String,
    val isMine: Boolean
)