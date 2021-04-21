package com.tdtd.data.response.reply

import com.tdtd.domain.entity.ReplyUserEntity

data class ReplyUserResponse(
    var code: Int,
    var message: String,
    var result: UserCommentResponse
) {
    fun toEntity() = ReplyUserEntity(
        code = code,
        message = message,
        result = result.toEntity()
    )
}