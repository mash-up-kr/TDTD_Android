package com.tdtd.data.api

import com.tdtd.data.request.ReplyUserCommentRequest
import com.tdtd.data.response.reply.ReplyUserResponse
import com.tdtd.data.response.room.RoomsResponse
import retrofit2.http.*
import com.tdtd.domain.Result

interface ReplyApi {

    @POST("api/v1/comments/{roomCode}")
    suspend fun postReplyUserComment(
        @Path("roomCode") roomCode: String,
        @Body replyUserCommentRequest: ReplyUserCommentRequest
    ): Result<ReplyUserResponse>

    @DELETE("/api/v1/comments")
    suspend fun deleteReplyUserComment(): Result<RoomsResponse>

    @POST("/api/v1/reports/{commentId}")
    suspend fun postReportUserByCommentId(
        @Path("commentId") commentId: Long
    ): Result<RoomsResponse>

}
