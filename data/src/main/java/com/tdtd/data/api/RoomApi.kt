package com.tdtd.data.api

import com.tdtd.data.request.MakeRoomRequest
import com.tdtd.data.response.room.*
import com.tdtd.domain.Result
import retrofit2.http.*


interface RoomApi {

    @POST("/api/v1/users/{roomCode}")
    suspend fun postParticipateByRoomCode(
        @Path("roomCode") roomCode: String
    ): Result<RoomsResponse>

    @GET("/api/v1/rooms")
    suspend fun getUserRoomList(): RoomsResponse

    @POST("/api/v1/rooms")
    suspend fun postCreateUserRoom(
        @Body makeRoomInfo: MakeRoomRequest
    ): CreatedRoomCodeResponse

    @DELETE("/api/v1/users/{roomCode}")
    suspend fun deleteParticipatedUserRoom(
        @Path("roomCode") roomCode: String
    ): DeleteResponse

    @GET("api/v1/rooms/{roomCode}")
    suspend fun getRoomDetailByRoomCode(
        @Path("roomCode") roomCode: String
    ): RoomDetailResponse
}
