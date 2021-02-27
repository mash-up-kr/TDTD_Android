package com.tdtd.domain.repository

/**
 * 관리자
 */
interface AdminRepository {

    /**
     * 방 호스트가 만든 방을 삭제한다.
     */
    fun deleteRoom(roomCode: Long)

    /**
     * 방 호스트가 다른 사용자의 답장을 삭제한다.
     */
    fun deleteOtherCommentByAdmin()

}
