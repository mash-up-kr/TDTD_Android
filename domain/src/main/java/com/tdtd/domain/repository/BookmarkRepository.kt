package com.tdtd.domain.repository

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.util.State

/**
 * 즐겨찾기
 */
interface BookmarkRepository {

    /**
     * 즐겨찾기 목록을 보여준다.
     */
    suspend fun getUserBookmarkList(): State<List<RoomEntity>>

    /**
     * 해당 room-code에 방을 즐겨찾기 목록에 추가한다.
     */
    suspend fun postBookmarkByRoomCode(roomCode: String): State<DeleteRoomEntity>

    /**
     * 해당 room-code에 방을 즐겨찾기 목록에서 삭제한다.
     */
    suspend fun deleteBookmarkByRoomCode(roomCode: String): State<DeleteRoomEntity>

}
