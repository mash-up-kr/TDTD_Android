package com.tdtd.domain.usecase

import com.tdtd.domain.Result
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.RoomRepository
import javax.inject.Inject

class GetAllRoomsUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): Result<List<RoomEntity>> =
        roomRepository.getUserRoomList()

    suspend operator fun invoke(makeRoomEntity: MakeRoomEntity): Result<CreatedRoomCodeEntity> =
        roomRepository.postCreateUserRoom(makeRoomEntity)

    suspend operator fun invoke(roomCode: String): Result<RoomDetailEntity> =
        roomRepository.getRoomDetailByRoomCode(roomCode)

    suspend fun deleteRoom(roomCode: String): Result<DeleteRoomEntity> =
        roomRepository.deleteParticipatedUserRoom(roomCode)

    suspend fun postParticipateByRoomCode(roomCode: String): Result<DeleteRoomEntity> =
        roomRepository.postParticipateByRoomCode(roomCode)
}
