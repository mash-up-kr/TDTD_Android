package com.tdtd.domain.repository

import com.tdtd.domain.Result
import com.tdtd.domain.entity.*
import okhttp3.RequestBody

/**
 * 답장
 */
interface ReplyRepository {

    /**
     * 사용자가 답장을 작성한다.
     */
    suspend fun postReplyUserComment(
        roomCode: String,
        params: HashMap<String, RequestBody>
    ): Result<DeleteRoomEntity>

    /**
     * 사용자가 작성한 답장을 삭제한다.
     */
    suspend fun deleteReplyUserComment(commentId: Long): Result<RoomDetailEntity>

    /**
     * 사용자가 작성된 답장을 신고한다.
     */
    suspend fun postReportUserByCommentId(commentId: Long): Result<DeleteRoomEntity>

}
