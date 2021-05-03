package com.tdtd.data.response.room

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.*

data class RoomDetailResponse(
    val code: String,
    val message: String,
    val result: ResultRoomInfoResponse
) {
    fun toEntity() = RoomDetailEntity(
        code = code,
        message = message,
        result = result.toEntity()
    )
}

data class ResultRoomInfoResponse(
    val title: String,
    val type: MakeRoomType,
    @SerializedName("share_url")
    val shareUrl: String,
    val comments: List<UserDetailCommentResponse>
) {
    fun toEntity() = ResultRoomInfoEntity(
        title = title,
        type = type,
        shareUrl = shareUrl,
        comments = comments.map { it.toEntity() }
    )
}

data class UserDetailCommentResponse(
    val id: Long,
    val nickname: String,
    val text: String? = null,
    @SerializedName("voice_file_url")
    val voiceFileUrl: String? = null,
    @SerializedName("sticker_color")
    val stickerColor: StickerColorType,
    @SerializedName("sticker_angle")
    val stickerAngle: Int,
    @SerializedName("created_at")
    val createAt: String,
    val isMine: Boolean
) {
    fun toEntity() = UserDetailCommentEntity(
        id = id,
        nickname = nickname,
        text = text,
        voiceFileUrl = voiceFileUrl,
        stickerColor = stickerColor,
        stickerAngle = stickerAngle,
        createAt = createAt,
        isMine = isMine
    )
}