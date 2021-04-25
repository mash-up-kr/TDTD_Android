package com.tdtd.data.api

import com.tdtd.data.response.admin.RoomUrlResponse
import com.tdtd.data.response.room.RoomsResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import com.tdtd.domain.Result

interface AdminApi {

    @DELETE("api/v1/rooms/{roomCode}")
    suspend fun deleteRoom(
        @Path("roomCode") roomCode: String
    ): Result<RoomsResponse>

    @GET("/api/v1/host/rooms/{roomCode}")
    suspend fun getSharedRoomUrl(
        @Path("roomCode") roomCode: String
    ): Result<RoomUrlResponse>

    @DELETE("api/v1/host/comments")
    suspend fun deleteOtherCommentByAdmin(): Result<RoomsResponse>
}
