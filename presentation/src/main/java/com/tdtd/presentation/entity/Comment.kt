package com.tdtd.presentation.entity

import com.tdtd.presentation.R

data class RoomContents(
    var id: Long?,
    var is_mine: Boolean,
    var nickname: String,
    var text: String,
    var voice_file_url: String,
    var sticker_color: String,
    var sticker_angle: Int,
    var created_at: String
)

fun getDefaultCharacter(stickerColor: String): Int {
    when (stickerColor) {
        "red" -> return R.drawable.img_character_1_center_default
        "yellow" -> return R.drawable.img_character_2_center_default
        "green" -> return R.drawable.img_character_3_center_default
        "blue" -> return R.drawable.img_character_4_center_default
        "lavender" -> return R.drawable.img_character_5_center_default
        "lightPink" -> return R.drawable.img_character_6_center_default
        "lightGreen" -> return R.drawable.img_character_7_center_default
    }
    return R.drawable.img_character_1_center_default
}

fun getSelectedCharacter(stickerColor: String): Int {
    when (stickerColor) {
        "red" -> return R.drawable.img_character_1_center_select
        "yellow" -> return R.drawable.img_character_2_center_select
        "green" -> return R.drawable.img_character_3_center_select
        "blue" -> return R.drawable.img_character_4_center_select
        "lavender" -> return R.drawable.img_character_5_center_select
        "lightPink" -> return R.drawable.img_character_6_center_select
        "lightGreen" -> return R.drawable.img_character_7_center_select
    }
    return R.drawable.img_character_1_center_default
}

fun getRoomContents(): List<RoomContents> {
    return listOf(
        RoomContents(
            0,
            true,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "red",
            10,
            "2020/21/32"
        ), RoomContents(
            1,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "yellow",
            0,
            "2020/21/32"
        ), RoomContents(
            2,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "green",
            -10,
            "2020/21/32"
        ), RoomContents(
            3,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "blue",
            0,
            "2020/21/32"
        ), RoomContents(
            4,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lavender",
            -10,
            "2020/21/32"
        ), RoomContents(
            5,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lightPink",
            0,
            "2020/21/32"
        ), RoomContents(
            6,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lightGreen",
            0,
            "2020/21/32"
        ), RoomContents(
            0,
            true,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "red",
            10,
            "2020/21/32"
        ), RoomContents(
            1,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "yellow",
            0,
            "2020/21/32"
        ), RoomContents(
            2,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "green",
            -10,
            "2020/21/32"
        ), RoomContents(
            3,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "blue",
            0,
            "2020/21/32"
        ), RoomContents(
            4,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lavender",
            -10,
            "2020/21/32"
        ), RoomContents(
            5,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lightPink",
            0,
            "2020/21/32"
        ), RoomContents(
            6,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lightGreen",
            0,
            "2020/21/32"
        ), RoomContents(
            0,
            true,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "red",
            10,
            "2020/21/32"
        ), RoomContents(
            1,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "yellow",
            0,
            "2020/21/32"
        ), RoomContents(
            2,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "green",
            -10,
            "2020/21/32"
        ), RoomContents(
            3,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "blue",
            0,
            "2020/21/32"
        ), RoomContents(
            4,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lavender",
            -10,
            "2020/21/32"
        ), RoomContents(
            5,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lightPink",
            0,
            "2020/21/32"
        ), RoomContents(
            6,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            "lightGreen",
            0,
            "2020/21/32"
        )
    )
}