package com.tdtd.domain.repository

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.ReplyUserEntity
import com.tdtd.domain.util.State
import okhttp3.MultipartBody

/**
 * 답장
 */
interface ReplyRepository {

    /**
     * 사용자가 답장을 작성한다.
     */
    suspend fun postReplyUserComment(
        roomCode: String,
        params: List<MultipartBody.Part>
    ): State<ReplyUserEntity>

    /**
     * 사용자가 작성한 답장을 삭제한다.
     */
    suspend fun deleteReplyUserComment(commentId: Long): State<DeleteRoomEntity>

    /**
     * 사용자가 작성된 답장을 신고한다.
     */
    suspend fun postReportUserByCommentId(commentId: Long): State<DeleteRoomEntity>

}
