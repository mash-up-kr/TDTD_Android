package com.tdtd.data.api

import com.tdtd.data.response.admin.ModifyRoomNameResponse
import com.tdtd.data.response.admin.RoomUrlResponse
import com.tdtd.data.response.room.DeleteResponse
import retrofit2.http.*

interface AdminApi {

    @DELETE("api/v1/host/rooms/{roomCode}")
    suspend fun deleteRoom(
        @Path("roomCode") roomCode: String
    ): DeleteResponse

    @GET("api/v1/host/rooms/{roomCode}")
    suspend fun getSharedRoomUrl(
        @Path("roomCode") roomCode: String
    ): RoomUrlResponse

    @DELETE("api/v1/host/comments/{commentId}")
    suspend fun deleteOtherCommentByAdmin(
        @Path("commentId") commentId: Long
    ): DeleteResponse
    
    @PATCH("api/v1/host/rooms/{roomCode}")
    suspend fun modifyRoomNameByHost(
        @Path("roomCode") roomCode: String,
        @Body roomName: ModifyRoomNameResponse
    ): DeleteResponse
}
