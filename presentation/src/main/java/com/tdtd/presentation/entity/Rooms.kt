package com.tdtd.presentation.entity

data class Rooms(
        var code: Int,
        var message: String,
        var rooms: List<Room>
)

fun getRooms(): List<Room> {
    return listOf(
            Room(
                    "room-2",
                    "FP2R5Uo-T8SO0WqoDRbvAg",
                    "https://sokdak.page.link/6nHzjLLc4kQuJn9v7",
                    "2021-04-10T03:21:42",
                    "false",
                    false,
                    false,
                    mutableListOf()
            ),
            Room(
                    "room-2",
                    "FP2R5Uo-T8SO0WqoDRbvAg",
                    "https://sokdak.page.link/6nHzjLLc4kQuJn9v7",
                    "2021-04-10T03:21:42",
                    "false",
                    false,
                    false,
                    mutableListOf()
            ),
    )
}
