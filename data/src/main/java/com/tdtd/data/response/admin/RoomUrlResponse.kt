package com.tdtd.data.response.admin

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.RoomUrlEntity
import com.tdtd.domain.entity.SharedUrlEntity

data class RoomUrlResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: SharedUrlResponse
) {
    fun toEntity() = RoomUrlEntity(
        code = code,
        message = message,
        result = result.toEntity()
    )
}

data class SharedUrlResponse(
    @SerializedName("share_url")
    val shareUrl: String
) {
    fun toEntity() = SharedUrlEntity(
        shareUrl = shareUrl
    )
}