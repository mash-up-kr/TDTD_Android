package com.tdtd.data.request

import com.tdtd.domain.entity.MakeRoomType

data class MakeRoomRequest(
    val id: String,
    val type: MakeRoomType
)