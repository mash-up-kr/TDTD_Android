package com.tdtd.domain.usecase

import com.tdtd.domain.entity.RoomDetailEntity
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class GetRoomDetailUseCase(private val repository: RoomRepository) :
    BaseUseCase.WithParam<String, State<RoomDetailEntity>> {

    override suspend fun invoke(param: String): State<RoomDetailEntity> =
        repository.getRoomDetailByRoomCode(param)
}