package com.tdtd.data.repository

import android.accounts.NetworkErrorException
import com.tdtd.data.api.RoomApi
import com.tdtd.data.mapper.toNetworkModel
import com.tdtd.domain.Result
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.entity.RoomCodeEntity
import com.tdtd.domain.entity.RoomDetailEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.RoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomApi: RoomApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    override suspend fun getUserRoomList(): Result<RoomsEntity> = withContext(ioDispatcher) {
        return@withContext try {
            roomApi.getUserRoomList().let {
//                if (it is Result.Success) {
                    Result.Success(it.toEntity())
//                } else {
//                    Result.Error(NetworkErrorException())
//                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postCreateUserRoom(makeRoomInfo: MakeRoomEntity): Result<RoomCodeEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.postCreateUserRoom(makeRoomInfo.toNetworkModel()).let {
                    if (it is Result.Success) {
                        Result.Success(RoomCodeEntity(it.data.roomCode))
                    } else {
                        Result.Error(NetworkErrorException())
                    }
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteParticipatedUserRoom(roomCode: String): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.deleteParticipatedUserRoom(roomCode).let {
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

    override suspend fun getRoomDetailByRoomCode(roomCode: String): Result<RoomDetailEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.getRoomDetailByRoomCode(roomCode).let {
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