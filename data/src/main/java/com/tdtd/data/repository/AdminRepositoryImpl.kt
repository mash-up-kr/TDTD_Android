package com.tdtd.data.repository

import com.tdtd.data.api.AdminApi
import com.tdtd.data.mapper.toNetworkModel
import com.tdtd.data.util.IoDispatcher
import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.ModifyRoomNameEntity
import com.tdtd.domain.entity.RoomUrlEntity
import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.util.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdminRepository {
    override suspend fun deleteRoom(roomCode: String): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteRoom(roomCode).let { roomResponse ->
                    State.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun getSharedRoomUrl(roomCode: String): State<RoomUrlEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.getSharedRoomUrl(roomCode).let { roomUrlResponse ->
                    State.Success(roomUrlResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun deleteOtherCommentByAdmin(commentId: Long): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteOtherCommentByAdmin(commentId).let { roomsResponse ->
                    State.Success(roomsResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun modifyRoomNameByHost(
        roomCode: String,
        roomName: ModifyRoomNameEntity
    ): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.modifyRoomNameByHost(roomCode, roomName.toNetworkModel())
                    .let { modifyRoomNameResponse ->
                        State.Success(modifyRoomNameResponse.toEntity())
                    }
            } catch (e: Exception) {
                State.Error(e)
            }
        }


}