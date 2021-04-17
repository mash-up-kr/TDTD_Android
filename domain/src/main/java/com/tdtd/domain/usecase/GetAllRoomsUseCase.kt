package com.tdtd.domain.usecase

import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.Result
import com.tdtd.domain.entity.RoomsEntity

class GetAllRoomsUseCase(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): Result<List<RoomsEntity>> =
        roomRepository.getUserRoomList()
}