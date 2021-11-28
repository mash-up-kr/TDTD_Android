package com.tdtd.domain.usecase

import com.tdtd.domain.entity.CreatedRoomCodeEntity
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class CreateUserRoomUseCase(private val repository: RoomRepository) :
    BaseUseCase.WithParam<MakeRoomEntity, State<CreatedRoomCodeEntity>> {

    override suspend fun invoke(param: MakeRoomEntity): State<CreatedRoomCodeEntity> =
        repository.postCreateUserRoom(param)
}