package com.tdtd.data.api

import com.tdtd.data.response.room.DeleteResponse
import com.tdtd.data.response.room.RoomResponse
import com.tdtd.data.response.room.RoomsResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.tdtd.domain.Result

interface BookmarkApi {

    @GET("api/v1/bookmarks")
    suspend fun getUserBookmarkList(): RoomsResponse

    /**
     * postBookmarkByRoomCode & deleteBookMarkByRoomCode 명세서 미완성
     */
    @POST("api/v1/bookmarks/{roomCode}")
    suspend fun postBookmarkByRoomCode(
        @Path("roomCode") roomCode: String
    ): DeleteResponse

    @DELETE("api/v1/bookmarks/{roomCode}")
    suspend fun deleteBookmarkByRoomCode(
        @Path("roomCode") roomCode: String
    ): DeleteResponse
}
