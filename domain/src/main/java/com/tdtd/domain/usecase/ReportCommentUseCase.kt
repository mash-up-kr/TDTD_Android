package com.tdtd.domain.usecase

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.repository.ReplyRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class ReportCommentUseCase(private val repository: ReplyRepository) :
    BaseUseCase.WithParam<Long, State<DeleteRoomEntity>> {

    override suspend fun invoke(param: Long): State<DeleteRoomEntity> =
        repository.postReportUserByCommentId(param)
}