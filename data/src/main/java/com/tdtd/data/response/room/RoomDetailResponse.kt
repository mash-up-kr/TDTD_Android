package com.tdtd.data.response.room

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.*

data class RoomDetailResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: ResultRoomInfoResponse
) {
    fun toEntity() = RoomDetailEntity(
        code = code,
        message = message,
        result = result.toEntity()
    )
}

data class ResultRoomInfoResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val typeEntity: RoomTypeEntity,
    @SerializedName("share_url")
    val shareUrl: String,
    @SerializedName("comments")
    val comments: List<UserDetailCommentResponse>
) {
    fun toEntity() = ResultRoomInfoEntity(
        title = title,
        typeEntity = typeEntity,
        shareUrl = shareUrl,
        comments = comments.map { it.toEntity() }
    )
}

data class UserDetailCommentResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("voice_file_url")
    val voiceFileUrl: String? = null,
    @SerializedName("sticker_color")
    val stickerColor: StickerColorType,
    @SerializedName("sticker_angle")
    val stickerAngle: Int,
    @SerializedName("created_at")
    val createAt: String,
    @SerializedName("is_mine")
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