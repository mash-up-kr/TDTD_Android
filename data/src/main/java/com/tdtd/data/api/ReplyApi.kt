package com.tdtd.data.api

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReplyApi {

    @GET("api/v1/comments/{roomCode}")
    fun postReplyUserComment(
        @Path("roomCode") roomCode: Long
    )

    @DELETE("/api/v1/comments")
    fun deleteReplyUserComment()

    @POST("/api/v1/reports/{commentId}")
    fun postReportUserByCommentId(
        @Path("commentId") commentId: Long
    )

}
