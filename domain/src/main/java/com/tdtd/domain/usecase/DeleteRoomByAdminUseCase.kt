package com.tdtd.domain.usecase

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class DeleteRoomByAdminUseCase(private val repository: AdminRepository) :
    BaseUseCase.WithParam<String, State<DeleteRoomEntity>> {

    override suspend fun invoke(param: String): State<DeleteRoomEntity> =
        repository.deleteRoom(param)
}