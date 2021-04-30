package com.tdtd.data.request

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.MakeRoomType

data class MakeRoomRequest(
    @SerializedName("title")
    val id: String,
    val type: MakeRoomType
)