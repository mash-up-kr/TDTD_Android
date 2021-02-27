package com.tdtd.data.api

import retrofit2.http.DELETE
import retrofit2.http.Path

interface AdminApi {

    @DELETE("api/v1/rooms/{roomCode}")
    fun deleteRoom(
        @Path("roomCode") roomCode: Long
    )

    @DELETE("api/v1/host/comments")
    fun deleteOtherCommentByAdmin()

}
