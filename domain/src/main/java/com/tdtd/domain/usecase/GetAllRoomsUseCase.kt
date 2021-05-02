package com.tdtd.domain.usecase

import com.tdtd.domain.Result
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.entity.RoomCodeEntity
import com.tdtd.domain.entity.RoomDetailEntity
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.repository.RoomRepository
import javax.inject.Inject

class GetAllRoomsUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): Result<List<RoomEntity>> =
        roomRepository.getUserRoomList()

    suspend operator fun invoke(makeRoomEntity: MakeRoomEntity): Result<RoomCodeEntity> =
        roomRepository.postCreateUserRoom(makeRoomEntity)
}
