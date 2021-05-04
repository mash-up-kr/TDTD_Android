package com.tdtd.domain.usecase

import com.tdtd.domain.Result
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.ReplyRepository
import javax.inject.Inject

class GetAllReplyUseCase @Inject constructor(
    private val replyRepository: ReplyRepository
) {
    suspend operator fun invoke(
        roomCode: String,
        replyUserCommentEntity: ReplyUserCommentWithFileEntity
    ): Result<DeleteRoomEntity> =
        replyRepository.postReplyUserComment(roomCode, replyUserCommentEntity)

    suspend operator fun invoke(
        commentId: Long
    ): Result<RoomDetailEntity> =
        replyRepository.deleteReplyUserComment(commentId)

    suspend fun postReportUserByCommentId(commentId: Long): Result<DeleteRoomEntity> =
        replyRepository.postReportUserByCommentId(commentId)
}