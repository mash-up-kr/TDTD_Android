package com.tdtd.data.request

import com.google.gson.annotations.SerializedName
import com.tdtd.domain.entity.RoomTypeEntity

data class MakeRoomRequest(
    @SerializedName("title")
    val id: String,
    @SerializedName("type")
    val typeEntity: RoomTypeEntity
)