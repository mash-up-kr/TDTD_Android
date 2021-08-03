package com.tdtd.data.repository

import com.tdtd.data.api.AdminApi
import com.tdtd.data.mapper.toNetworkModel
import com.tdtd.domain.IoDispatcher
import com.tdtd.domain.Result
import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.ModifyRoomNameEntity
import com.tdtd.domain.entity.RoomUrlEntity
import com.tdtd.domain.repository.AdminRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdminRepository {
    override suspend fun deleteRoom(roomCode: String): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteRoom(roomCode).let { roomResponse ->
                    Result.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getSharedRoomUrl(roomCode: String): Result<RoomUrlEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.getSharedRoomUrl(roomCode).let { roomUrlResponse ->
                    Result.Success(roomUrlResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteOtherCommentByAdmin(commentId: Long): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteOtherCommentByAdmin(commentId).let { roomsResponse ->
                    Result.Success(roomsResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun modifyRoomNameByHost(
        roomCode: String,
        roomName: ModifyRoomNameEntity
    ): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.modifyRoomNameByHost(roomCode, roomName.toNetworkModel())
                    .let { modifyRoomNameResponse ->
                        Result.Success(modifyRoomNameResponse.toEntity())
                    }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


}