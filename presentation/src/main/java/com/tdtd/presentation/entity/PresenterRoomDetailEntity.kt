package com.tdtd.presentation.entity

import com.tdtd.domain.entity.RoomTypeEntity
import com.tdtd.presentation.R

data class PresenterRoomDetailEntity(
    val code: String,
    val message: String,
    val result: PresenterResultRoomInfoEntity
)

data class PresenterResultRoomInfoEntity(
    val title: String,
    val typeEntity: RoomTypeEntity,
    val shareUrl: String,
    val comments: List<Comments>
)

data class Comments(
    var id: Long?,
    var is_mine: Boolean,
    var nickname: String,
    var text: String?,
    var voice_file_url: String?,
    var presenterSticker_color: PresenterStickerColorType,
    var sticker_angle: Int,
    var created_at: String
)

fun getDefaultCharacter(presenterStickerColor: PresenterStickerColorType): Int {
    return when (presenterStickerColor) {
        PresenterStickerColorType.RED -> R.drawable.img_character_1_center_default
        PresenterStickerColorType.YELLOW -> R.drawable.img_character_2_center_default
        PresenterStickerColorType.GREEN -> R.drawable.img_character_3_center_default
        PresenterStickerColorType.BLUE -> R.drawable.img_character_4_center_default
        PresenterStickerColorType.LAVENDER -> R.drawable.img_character_5_center_default
        PresenterStickerColorType.LIGHT_PINK -> R.drawable.img_character_6_center_default
        PresenterStickerColorType.LIGHT_GREEN -> R.drawable.img_character_7_center_default
    }
}

fun getSelectedCharacter(presenterStickerColor: PresenterStickerColorType): Int {
    return when (presenterStickerColor) {
        PresenterStickerColorType.RED -> R.drawable.img_character_1_center_select
        PresenterStickerColorType.YELLOW -> R.drawable.img_character_2_center_select
        PresenterStickerColorType.GREEN -> R.drawable.img_character_3_center_select
        PresenterStickerColorType.BLUE -> R.drawable.img_character_4_center_select
        PresenterStickerColorType.LAVENDER -> R.drawable.img_character_5_center_select
        PresenterStickerColorType.LIGHT_PINK -> R.drawable.img_character_6_center_select
        PresenterStickerColorType.LIGHT_GREEN -> R.drawable.img_character_7_center_select
    }
}