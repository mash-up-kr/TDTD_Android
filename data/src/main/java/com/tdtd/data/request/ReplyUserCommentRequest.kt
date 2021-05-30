package com.tdtd.data.request

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.domain.entity.StickerColorType

data class ReplyUserCommentRequest(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("message_type")
    val messageType: MakeRoomType,
    @SerializedName("text_message")
    val textMessage: String?,
    @SerializedName("voice_file")
    val voiceFile: String?,
    @SerializedName("sticker_color")
    val stickerColor: StickerColorType,
    @SerializedName("sticker_angle")
    val stickerAngle: Int
)
