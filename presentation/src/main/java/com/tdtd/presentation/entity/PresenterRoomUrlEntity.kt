package com.tdtd.presentation.entity

data class PresenterRoomUrlEntity(
    var code: Int,
    var message: String,
    var result: PresenterSharedUrlEntity
)

data class PresenterSharedUrlEntity(
    val shareUrl: String
)