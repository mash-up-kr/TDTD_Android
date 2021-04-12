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
                    false,
                    false
            ),
            Room(
                    "room-4",
                    "hVc7BO_8RuiEh3lLTxmx5A",
                    "https://sokdak.page.link/GJ6U2DkGeQjY39Qk8",
                    "2021-04-10T03:21:43",
                    false,
                    false
            ),
            Room(
                    "room-5",
                    "crFr3OwoQWiG0uHyCcHxbQ",
                    "https://sokdak.page.link/QtiY1ydsrKuAvhka6",
                    "2021-04-10T03:21:43",
                    false,
                    true
            ),
            Room(
                    "room-6",
                    "v9pP5c__SiKArnwlAeihcA",
                    "https://sokdak.page.link/gzwUYYdiVb3FUjJK6",
                    "2021-04-10T03:21:44",
                    false,
                    false
            ),
            Room(
                    "room-8",
                    "7T36t1gPS3Om0LBDaqmb-A",
                    "https://sokdak.page.link/TeMiWfTVwryvTkbY6",
                    "2021-04-10T03:21:45",
                    false,
                    false
            )
    )
}