package com.tdtd.domain.usecase

import com.tdtd.domain.entity.ReplyUserEntity
import com.tdtd.domain.repository.ReplyRepository
import com.tdtd.domain.util.State
import okhttp3.MultipartBody

class GetAllReplyUseCase(private val replyRepository: ReplyRepository) {

    suspend operator fun invoke(
        roomCode: String,
        params: List<MultipartBody.Part>
    ): State<ReplyUserEntity> =
        replyRepository.postReplyUserComment(roomCode, params)
}