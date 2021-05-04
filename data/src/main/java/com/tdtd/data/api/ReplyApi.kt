package com.tdtd.data.api

import com.tdtd.data.request.ReplyUserCommentRequest
import com.tdtd.data.response.reply.ReplyUserResponse
import com.tdtd.data.response.room.DeleteResponse
import com.tdtd.data.response.room.RoomDetailResponse
import com.tdtd.data.response.room.RoomResponse
import com.tdtd.data.response.room.RoomsResponse
import com.tdtd.domain.Result
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ReplyApi {

    @POST("api/v1/comments/{roomCode}")
    suspend fun postReplyUserComment(
        @Path("roomCode") roomCode: String,
        @Body replyUserCommentRequest: ReplyUserCommentRequest
    ): DeleteResponse

    @DELETE("/api/v1/comments/{commentId}")
    suspend fun deleteReplyUserComment(
        @Path("commentId") commentId: Long
    ): RoomDetailResponse

    @POST("/api/v1/reports/{commentId}")
    suspend fun postReportUserByCommentId(
        @Path("commentId") commentId: Long
    ) : DeleteResponse
}
