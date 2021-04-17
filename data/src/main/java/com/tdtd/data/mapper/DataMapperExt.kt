package com.tdtd.data.mapper

import com.tdtd.data.request.MakeRoomRequest
import com.tdtd.domain.entity.MakeRoomEntity

fun MakeRoomEntity.toNetworkModel() = MakeRoomRequest(
    this.id,
    this.type
)