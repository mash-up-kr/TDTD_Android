package com.tdtd.data.repository

import android.accounts.NetworkErrorException
import com.tdtd.data.api.ReplyApi
import com.tdtd.data.mapper.toNetworkModel
import com.tdtd.domain.IoDispatcher
import com.tdtd.domain.Result
import com.tdtd.domain.entity.ReplyUserCommentWithFileEntity
import com.tdtd.domain.entity.ReplyUserEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.ReplyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReplyRepositoryImpl @Inject constructor(
    private val replyApi: ReplyApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReplyRepository {
    override suspend fun postReplyUserComment(
        roomCode: String,
        replyUserCommentEntity: ReplyUserCommentWithFileEntity
    ): Result<ReplyUserEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.postReplyUserComment(
                    roomCode,
                    replyUserCommentEntity.toNetworkModel()
                ).let {
                    if (it is Result.Success) {
                        Result.Success(it.data.toEntity())
                    } else {
                        Result.Error(NetworkErrorException())
                    }
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteReplyUserComment(): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.deleteReplyUserComment().let {
                    if (it is Result.Success) {
                        Result.Success(it.data.toEntity())
                    } else {
                        Result.Error(NetworkErrorException())
                    }
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


    override suspend fun postReportUserByCommentId(commentId: Long): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                replyApi.postReportUserByCommentId(
                    commentId
                ).let {
                    if (it is Result.Success) {
                        Result.Success(it.data.toEntity())
                    } else {
                        Result.Error(NetworkErrorException())
                    }
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}