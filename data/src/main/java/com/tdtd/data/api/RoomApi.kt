package com.tdtd.data.api

import com.tdtd.data.request.MakeRoomRequest
import com.tdtd.data.response.room.RoomCodeResponse
import com.tdtd.data.response.room.RoomDetailResponse
import com.tdtd.data.response.room.RoomsResponse
import retrofit2.http.*
import com.tdtd.domain.Result


interface RoomApi {

    @POST("/api/v1/users/{roomCode}")
    fun postParticipateByRoomCode(
        @Path("roomCode") roomCode: String
    ): Result<RoomsResponse>

    @GET("/api/v1/rooms")
    fun getUserRoomList(): RoomsResponse

    @POST("/api/v1/rooms")
    fun postCreateUserRoom(
        @Body makeRoomInfo: MakeRoomRequest
    ): Result<RoomCodeResponse>

    @DELETE("/api/v1/users/{roomCode}")
    fun deleteParticipatedUserRoom(
        @Path("roomCode") roomCode: String
    ): Result<RoomsResponse>

    @GET("api/v1/rooms/{roomCode}")
    fun getRoomDetailByRoomCode(
        @Path("roomCode") roomCode: String
    ): Result<RoomDetailResponse>

}
