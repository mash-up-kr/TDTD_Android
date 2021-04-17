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
        @Path("roomCode") roomCode: Long
    ): Result<RoomsResponse>

    @GET("/api/v1/rooms")
    fun getUserRoomList(): Result<List<RoomsResponse>>

    @POST("/api/v1/rooms")
    fun postCreateUserRoom(makeRoomInfo: MakeRoomRequest): Result<RoomCodeResponse>

    @DELETE("/api/v1/users/{roomCode}")
    fun deleteParticipatedUserRoom(
        @Path("roomCode") roomCode: Long
    ): Result<RoomsResponse>

    @GET("api/v1/rooms/{roomCode}")
    fun getRoomDetailByRoomCode(
        @Path("roomCode") roomCode: Long
    ): Result<RoomDetailResponse>

}
