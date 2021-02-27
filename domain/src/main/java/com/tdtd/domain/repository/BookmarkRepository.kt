package com.tdtd.domain.repository

/**
 * 즐겨찾기
 */
interface BookmarkRepository {

    /**
     * 즐겨찾기 목록을 보여준다.
     */
    fun getUserBookmarkList()

    /**
     * 해당 room-code에 방을 즐겨찾기 목록에 추가한다.
     */
    fun postBookmarkByRoomCode(roomCode: Long)

    /**
     * 해당 room-code에 방을 즐겨찾기 목록에서 삭제한다.
     */
    fun deleteBookmarkByRoomCode(roomCode: Long)

}
