package com.tdtd.domain.usecase

import com.tdtd.domain.Result
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.RoomRepository
import javax.inject.Inject

class GetAllRoomsUseCase @Inject constructor(
    val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): Result<RoomsEntity> =
        roomRepository.getUserRoomList()
}