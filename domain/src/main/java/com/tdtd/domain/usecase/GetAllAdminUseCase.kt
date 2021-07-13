package com.tdtd.domain.usecase

import com.tdtd.domain.Result
import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.RoomUrlEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.AdminRepository
import javax.inject.Inject

class GetAllAdminUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(roomCode: String): Result<DeleteRoomEntity> =
        adminRepository.deleteRoom(roomCode)

    suspend operator fun invoke(commentId: Long): Result<DeleteRoomEntity> =
        adminRepository.deleteOtherCommentByAdmin(commentId)

    suspend fun getSharedRoomUrl(roomCode: String): Result<RoomUrlEntity> =
        adminRepository.getSharedRoomUrl(roomCode)
}
