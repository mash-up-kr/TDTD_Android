package com.tdtd.domain.repository

import com.tdtd.domain.entity.*
import com.tdtd.domain.util.State

/**
 * 롤링페이퍼 방
 */
interface RoomRepository {

    /**
     * 링크를 통해 초대된 사용자가 room-code에 해당하는 방에 참여한다.
     */
    suspend fun postParticipateByRoomCode(roomCode: String): State<DeleteRoomEntity>

    /**
     * 사용자의 방 목록을 가져온다.
     */
    suspend fun getUserRoomList(): State<List<RoomEntity>>

    /**
     * 사용자가 방을 만든다.
     */
    suspend fun postCreateUserRoom(makeRoomInfo: MakeRoomEntity): State<CreatedRoomCodeEntity>

    /**
     * 사용자가 방을 나간다.
     */
    suspend fun deleteParticipatedUserRoom(roomCode: String): State<DeleteRoomEntity>

    /**
     * room-code에 해당하는 방의 상세 정보를 가져온다.
     */
    suspend fun getRoomDetailByRoomCode(roomCode: String): State<RoomDetailEntity>

}
