package com.tdtd.data.request

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.MakeRoomType

data class ReplyUserCommentRequest(
    val nickname: String,
    @SerializedName("message_type")
    val messageType: MakeRoomType,
    @SerializedName("text_message")
    val textMessage: String?,
    @SerializedName("voice_file")
    val voiceFile: String?,
    @SerializedName("sticker_color")
    val stickerColor: String,     //todo: sehee Enum Type 으로 변경해야함
    @SerializedName("sticker_angle")
    val stickerAngle: Int
)
