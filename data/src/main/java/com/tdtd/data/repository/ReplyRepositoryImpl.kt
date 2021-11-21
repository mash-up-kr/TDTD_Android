package com.tdtd.data.repository

import com.tdtd.data.api.ReplyApi
import com.tdtd.data.util.IoDispatcher
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.ReplyRepository
import com.tdtd.domain.util.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class ReplyRepositoryImpl @Inject constructor(
    private val replyApi: ReplyApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReplyRepository {
    override suspend fun postReplyUserComment(
        roomCode: String,
        params: List<MultipartBody.Part>
    ): State<ReplyUserEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.postReplyUserComment(
                    roomCode,
                    params
                ).let { replyUserResponse ->
                    State.Success(replyUserResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun deleteReplyUserComment(commentId: Long): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.deleteReplyUserComment(commentId).let { roomResponse ->
                    State.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }


    override suspend fun postReportUserByCommentId(commentId: Long): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.postReportUserByCommentId(commentId).let { roomsResponse ->
                    State.Success(roomsResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }
}