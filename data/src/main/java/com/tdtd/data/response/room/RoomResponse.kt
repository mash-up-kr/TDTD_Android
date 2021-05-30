package com.tdtd.data.response.room

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.RoomEntity

/**
 * RoomEntity
 * @param title
 * @param roomCode
 * @param shareUrl
 * @param createdAt
 * @param isBookmark
 * @param isHost
 */
data class RoomResponse(
    @SerializedName(value = "title")
    var title: String,
    @SerializedName(value = "room_code")
    var roomCode: String,
    @SerializedName("share_url")
    var shareUrl: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName(value = "is_bookmark")
    var isBookmark: Boolean,
    @SerializedName("is_host")
    var isHost: Boolean
) {
    fun toEntity() = RoomEntity(
        title = title,
        roomCode = roomCode,
        shareUrl = shareUrl,
        createdAt = createdAt,
        isBookmark = isBookmark,
        isHost = isHost
    )
}
