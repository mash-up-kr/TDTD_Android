package com.tdtd.presentation.entity

import java.io.Serializable

data class Rooms(
        var code: Int,
        var message: String,
        var rooms: List<Room>
) : Serializable
