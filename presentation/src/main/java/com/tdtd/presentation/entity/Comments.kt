package com.tdtd.presentation.entity

import com.tdtd.presentation.R

data class Comments(
    var id: Long?,
    var is_mine: Boolean,
    var nickname: String,
    var text: String?,
    var voice_file_url: String?,
    var sticker_color: StickerColorType,
    var sticker_angle: Int,
    var created_at: String
)

fun getDefaultCharacter(stickerColor: StickerColorType): Int {
    return when (stickerColor) {
        StickerColorType.RED -> R.drawable.img_character_1_center_default
        StickerColorType.YELLOW -> R.drawable.img_character_2_center_default
        StickerColorType.GREEN -> R.drawable.img_character_3_center_default
        StickerColorType.BLUE -> R.drawable.img_character_4_center_default
        StickerColorType.LAVENDER -> R.drawable.img_character_5_center_default
        StickerColorType.LIGHT_PINK -> R.drawable.img_character_6_center_default
        StickerColorType.LIGHT_GREEN -> R.drawable.img_character_7_center_default
    }
}

fun getSelectedCharacter(stickerColor: StickerColorType): Int {
    return when (stickerColor) {
        StickerColorType.RED -> R.drawable.img_character_1_center_select
        StickerColorType.YELLOW -> R.drawable.img_character_2_center_select
        StickerColorType.GREEN -> R.drawable.img_character_3_center_select
        StickerColorType.BLUE -> R.drawable.img_character_4_center_select
        StickerColorType.LAVENDER -> R.drawable.img_character_5_center_select
        StickerColorType.LIGHT_PINK -> R.drawable.img_character_6_center_select
        StickerColorType.LIGHT_GREEN -> R.drawable.img_character_7_center_select
    }
}

fun getComments(): List<Comments> {
    return listOf(
        Comments(
            0,
            true,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.RED,
            10,
            "2020/21/32"
        ), Comments(
            1,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.YELLOW,
            0,
            "2020/21/32"
        ), Comments(
            2,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.GREEN,
            -10,
            "2020/21/32"
        ), Comments(
            3,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.BLUE,
            0,
            "2020/21/32"
        ), Comments(
            4,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LAVENDER,
            -10,
            "2020/21/32"
        ), Comments(
            5,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LIGHT_PINK,
            0,
            "2020/21/32"
        ), Comments(
            6,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LIGHT_GREEN,
            0,
            "2020/21/32"
        ), Comments(
            0,
            true,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.RED,
            10,
            "2020/21/32"
        ), Comments(
            1,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.YELLOW,
            0,
            "2020/21/32"
        ), Comments(
            2,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.GREEN,
            -10,
            "2020/21/32"
        ), Comments(
            3,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.BLUE,
            0,
            "2020/21/32"
        ), Comments(
            4,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LAVENDER,
            -10,
            "2020/21/32"
        ), Comments(
            5,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LIGHT_PINK,
            0,
            "2020/21/32"
        ), Comments(
            6,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LIGHT_GREEN,
            0,
            "2020/21/32"
        ), Comments(
            0,
            true,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.RED,
            10,
            "2020/21/32"
        ), Comments(
            1,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.YELLOW,
            0,
            "2020/21/32"
        ), Comments(
            2,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.GREEN,
            -10,
            "2020/21/32"
        ), Comments(
            3,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.BLUE,
            0,
            "2020/21/32"
        ), Comments(
            4,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LAVENDER,
            -10,
            "2020/21/32"
        ), Comments(
            5,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LIGHT_PINK,
            0,
            "2020/21/32"
        ), Comments(
            6,
            false,
            "고민지",
            "안녕 나는 고민지야",
            "www.naver.com",
            StickerColorType.LIGHT_GREEN,
            0,
            "2020/21/32"
        )
    )
}