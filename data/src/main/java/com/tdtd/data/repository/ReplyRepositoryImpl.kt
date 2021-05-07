package com.tdtd.data.repository

import com.tdtd.data.api.ReplyApi
import com.tdtd.data.mapper.toNetworkModel
import com.tdtd.domain.IoDispatcher
import com.tdtd.domain.Result
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.ReplyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject

class ReplyRepositoryImpl @Inject constructor(
    private val replyApi: ReplyApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReplyRepository {
    override suspend fun postReplyUserComment(
        roomCode: String,
        params: HashMap<String, RequestBody>
    ): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.postReplyUserComment(
                    roomCode,
                    params
                ).let { replyUserResponse ->
                    Result.Success(replyUserResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteReplyUserComment(commentId: Long): Result<RoomDetailEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.deleteReplyUserComment(commentId).let { roomResponse ->
                    Result.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


    override suspend fun postReportUserByCommentId(commentId: Long): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.postReportUserByCommentId(commentId).let { roomsResponse ->
                    Result.Success(roomsResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}