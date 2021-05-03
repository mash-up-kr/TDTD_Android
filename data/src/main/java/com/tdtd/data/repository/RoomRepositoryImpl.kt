package com.tdtd.data.repository

import android.accounts.NetworkErrorException
import com.tdtd.data.api.RoomApi
import com.tdtd.data.mapper.toListRoomEntity
import com.tdtd.data.mapper.toNetworkModel
import com.tdtd.domain.IoDispatcher
import com.tdtd.domain.Result
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.RoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomApi: RoomApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RoomRepository {
    override suspend fun postParticipateByRoomCode(roomCode: String): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.postParticipateByRoomCode(roomCode).let {
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

    override suspend fun getUserRoomList(): Result<List<RoomEntity>> = withContext(ioDispatcher) {
        return@withContext try {
            roomApi.getUserRoomList().let { roomsResponse ->
                Result.Success(roomsResponse.toListRoomEntity())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postCreateUserRoom(makeRoomInfo: MakeRoomEntity): Result<CreatedRoomCodeEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.postCreateUserRoom(makeRoomInfo.toNetworkModel())
                    .let { createdRoomCodeResponse ->
                        Result.Success(createdRoomCodeResponse.toEntity())
                    }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteParticipatedUserRoom(roomCode: String): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.deleteParticipatedUserRoom(roomCode).let { roomsResponse ->
                    Result.Success(roomsResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getRoomDetailByRoomCode(roomCode: String): Result<RoomDetailEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.getRoomDetailByRoomCode(roomCode).let { roomDetailResponse ->
                    Result.Success(roomDetailResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}