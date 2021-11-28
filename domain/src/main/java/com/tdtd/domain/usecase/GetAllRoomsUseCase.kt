package com.tdtd.domain.usecase

import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class GetAllRoomsUseCase(private val repository: RoomRepository) :
    BaseUseCase.NoParam<State<List<RoomEntity>>> {

    override suspend fun invoke(): State<List<RoomEntity>> = repository.getUserRoomList()
}
