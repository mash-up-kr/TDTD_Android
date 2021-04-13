package com.tdtd.data.entity

import com.google.gson.annotations.SerializedName

/**
 * RoomEntity
 * @param title
 * @param room_code
 * @param share_url
 * @param created_at
 * @param is_bookmark
 * @param is_host
 */
data class RoomEntity(
        @SerializedName("title")
        var title: String,
        @SerializedName(value = "room_code")
        var room_code: String,
        @SerializedName("share_url")
        var share_url: String,
        @SerializedName("created_at")
        var created_at: String,
        @SerializedName(value = "is_bookmark")
        var is_bookmark: Boolean,
        @SerializedName("is_host")
        var is_host: Boolean
)
