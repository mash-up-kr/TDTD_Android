package com.tdtd.domain.repository

/**
 * 롤링페이퍼 방
 */
interface RoomRepository {

    /**
     * 링크를 통해 초대된 사용자가 room-code에 해당하는 방에 참여한다.
     */
    fun postParticipateByRoomCode(roomCode: Long)

    /**
     * 사용자의 방 목록을 가져온다.
     */
    fun getUserRoomList()

    /**
     * 사용자가 방을 만든다.
     */
    fun postCreateUserRoom()

    /**
     * 사용자가 방을 나간다.
     */
    fun deleteParticipatedUserRoom(roomCode: Long)

    /**
     * room-code에 해당하는 방의 상세 정보를 가져온다.
     */
    fun getRoomDetailByRoomCode(roomCode: Long)

}
