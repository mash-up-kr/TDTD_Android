package com.tdtd.data.response.admin

import com.google.gson.annotations.SerializedName

data class ModifyRoomNameResponse(
    @SerializedName("new_title")
    val roomName: String
)