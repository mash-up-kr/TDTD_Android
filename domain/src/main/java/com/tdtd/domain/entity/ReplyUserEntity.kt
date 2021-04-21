package com.tdtd.domain.entity

data class ReplyUserEntity(
    var code: Int,
    var message: String,
    var result: ReplyUserCommentWithUrlEntity
)