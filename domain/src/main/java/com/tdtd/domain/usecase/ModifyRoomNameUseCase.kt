package com.tdtd.domain.usecase

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.ModifyRoomNameEntity
import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class ModifyRoomNameUseCase(private val repository: AdminRepository) :
    BaseUseCase.WithMultipleParam<String, ModifyRoomNameEntity, State<DeleteRoomEntity>> {

    override suspend fun invoke(
        param1: String,
        param2: ModifyRoomNameEntity
    ): State<DeleteRoomEntity> = repository.modifyRoomNameByHost(param1, param2)
}
