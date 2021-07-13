package com.tdtd.domain.usecase

import com.tdtd.domain.Result
import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.ReplyUserEntity
import com.tdtd.domain.entity.RoomDetailEntity
import com.tdtd.domain.repository.ReplyRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class GetAllReplyUseCase @Inject constructor(
    private val replyRepository: ReplyRepository
) {
    suspend operator fun invoke(
        roomCode: String,
        params: List<MultipartBody.Part>
    ): Result<ReplyUserEntity> =
        replyRepository.postReplyUserComment(roomCode, params)

    suspend operator fun invoke(
        commentId: Long
    ): Result<DeleteRoomEntity> =
        replyRepository.deleteReplyUserComment(commentId)

    suspend fun postReportUserByCommentId(commentId: Long): Result<DeleteRoomEntity> =
        replyRepository.postReportUserByCommentId(commentId)
}