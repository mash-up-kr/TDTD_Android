package com.tdtd.domain.repository

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.ModifyRoomNameEntity
import com.tdtd.domain.entity.RoomUrlEntity
import com.tdtd.domain.util.State

/**
 * 관리자
 */
interface AdminRepository {

    /**
     * 방 호스트가 만든 방을 삭제한다.
     */
    suspend fun deleteRoom(roomCode: String): State<DeleteRoomEntity>

    /**
     * 방의 공유 URL 을 얻어온다.
     */
    suspend fun getSharedRoomUrl(roomCode: String): State<RoomUrlEntity>

    /**
     * 방 호스트가 다른 사용자의 답장을 삭제한다.
     */
    suspend fun deleteOtherCommentByAdmin(commentId: Long): State<DeleteRoomEntity>

    /**
     * 방 호스트가 방 제목 수정.
     */
    suspend fun modifyRoomNameByHost(
        roomCode: String,
        roomName: ModifyRoomNameEntity
    ): State<DeleteRoomEntity>
}
