package com.tdtd.data.api

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarkApi {

    @GET("api/v1/bookmarks")
    fun getUserBookmarkList()

    @POST("api/v1/bookmarks/{roomCode}")
    fun postBookmarkByRoomCode(
        @Path("roomCode") roomCode: Long
    )

    @DELETE("api/v1/bookmarks/{roomCode}")
    fun deleteBookmarkByRoomCode(
        @Path("roomCode") roomCode: Long
    )
}
