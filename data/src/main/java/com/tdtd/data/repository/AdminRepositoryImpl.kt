package com.tdtd.data.repository

import android.accounts.NetworkErrorException
import com.tdtd.data.api.AdminApi
import com.tdtd.domain.IoDispatcher
import com.tdtd.domain.Result
import com.tdtd.domain.entity.RoomUrlEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.AdminRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdminRepository {
    override suspend fun deleteRoom(roomCode: String): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteRoom(roomCode).let { roomResponse ->
                    Result.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getSharedRoomUrl(roomCode: String) : Result<RoomUrlEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.getSharedRoomUrl(roomCode).let { roomUrlResponse ->
                    Result.Success(roomUrlResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteOtherCommentByAdmin(commentId: Long): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteOtherCommentByAdmin(commentId).let { roomsResponse ->
                    Result.Success(roomsResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}