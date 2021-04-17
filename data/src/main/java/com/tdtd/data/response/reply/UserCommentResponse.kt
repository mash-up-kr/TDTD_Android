package com.tdtd.data.response.reply

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.domain.entity.ReplyUserCommentWithFileEntity
import com.tdtd.domain.entity.ReplyUserCommentWithUrlEntity

data class UserCommentResponse(
    val nickname: String,
    @SerializedName("message_type")
    val messageType: MakeRoomType,
    @SerializedName("text_message")
    val textMessage: String?,
    @SerializedName("voice_url")
    val voiceUrl: String?,
    @SerializedName("sticker_color")
    val stickerColor: String,     //todo: sehee Enum Type 으로 변경해야함
    @SerializedName("sticker_angle")
    val stickerAngle: Int
) {
    fun toEntity() = ReplyUserCommentWithUrlEntity(
        nickname = nickname,
        messageType = messageType,
        textMessage = textMessage,
        voiceUrl = voiceUrl,
        stickerColor = stickerColor,
        stickerAngle = stickerAngle
    )
}
