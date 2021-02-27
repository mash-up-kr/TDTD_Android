package com.tdtd.domain.repository

/**
 * 답장
 */
interface ReplyRepository {

    /**
     * 사용자가 답장을 작성한다.
     */
    fun postReplyUserComment(roomCode: Long)

    /**
     * 사용자가 작성한 답장을 삭제한다.
     */
    fun deleteReplyUserComment()

    /**
     * 사용자가 작성된 답장을 신고한다.
     */
    fun postReportUserByCommentId(commentId: Long)

}
