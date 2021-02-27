package com.tdtd.data.api

import retrofit2.http.*

interface RoomApi {

    @POST("/api/v1/users/{roomCode}")
    fun postParticipateByRoomCode(
        @Path("roomCode") roomCode: Long
    )

    @GET("/api/v1/rooms")
    fun getUserRoomList()

    @POST("/api/v1/roms")
    fun postCreateUserRoom()

    @DELETE("/api/v1/users/{roomCode}")
    fun deleteParticipatedUserRoom(
        @Path("roomCode") roomCode: Long
    )

    @GET("api/v1/rooms/{roomCode}")
    fun getRoomDetailByRoomCode(
        @Path("roomCode") roomCode: Long
    )

}
