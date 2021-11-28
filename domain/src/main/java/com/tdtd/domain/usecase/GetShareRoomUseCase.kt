package com.tdtd.domain.usecase

import com.tdtd.domain.entity.RoomUrlEntity
import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class GetShareRoomUseCase(private val repository: AdminRepository) :
    BaseUseCase.WithParam<String, State<RoomUrlEntity>> {

    override suspend fun invoke(param: String): State<RoomUrlEntity> =
        repository.getSharedRoomUrl(param)
}