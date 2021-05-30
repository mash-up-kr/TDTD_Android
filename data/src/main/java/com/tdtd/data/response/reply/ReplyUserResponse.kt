package com.tdtd.data.response.reply

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.ReplyUserEntity

data class ReplyUserResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: UserCommentResponse
) {
    fun toEntity() = ReplyUserEntity(
        code = code,
        message = message,
        result = result.toEntity()
    )
}