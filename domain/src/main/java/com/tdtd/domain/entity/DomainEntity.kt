package com.tdtd.domain.entity

sealed class DomainEntity {

    data class Dummy (
        var id: Long? = 0
    ): DomainEntity()

}