package com.tdtd.presentation.entity

sealed class PresentationEntity {
    data class Dummy (
        var id: Long?
    ): PresentationEntity()
}
