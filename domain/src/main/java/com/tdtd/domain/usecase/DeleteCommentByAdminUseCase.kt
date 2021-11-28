package com.tdtd.domain.usecase

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class DeleteCommentByAdminUseCase(private val repository: AdminRepository) :
    BaseUseCase.WithParam<Long, State<DeleteRoomEntity>> {

    override suspend fun invoke(param: Long): State<DeleteRoomEntity> =
        repository.deleteOtherCommentByAdmin(param)
}