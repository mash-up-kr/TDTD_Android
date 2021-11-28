package com.tdtd.domain.usecase

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class EnterByRoomCodeUseCase(private val repository: RoomRepository) :
    BaseUseCase.WithParam<String, State<DeleteRoomEntity>> {

    override suspend fun invoke(param: String): State<DeleteRoomEntity> =
        repository.postParticipateByRoomCode(param)
}