package com.tdtd.data.repository

import com.tdtd.data.api.RoomApi
import com.tdtd.data.mapper.toListRoomEntity
import com.tdtd.data.mapper.toNetworkModel
import com.tdtd.data.util.IoDispatcher
import com.tdtd.domain.entity.*
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.util.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomApi: RoomApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RoomRepository {
    override suspend fun postParticipateByRoomCode(roomCode: String): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.postParticipateByRoomCode(roomCode).let { roomDetailResponse ->
                    State.Success(roomDetailResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun getUserRoomList(): State<List<RoomEntity>> = withContext(ioDispatcher) {
        return@withContext try {
            roomApi.getUserRoomList().let { roomsResponse ->
                State.Success(roomsResponse.toListRoomEntity())
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }

    override suspend fun postCreateUserRoom(makeRoomInfo: MakeRoomEntity): State<CreatedRoomCodeEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.postCreateUserRoom(makeRoomInfo.toNetworkModel())
                    .let { createdRoomCodeResponse ->
                        State.Success(createdRoomCodeResponse.toEntity())
                    }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun deleteParticipatedUserRoom(roomCode: String): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.deleteParticipatedUserRoom(roomCode).let { roomsResponse ->
                    State.Success(roomsResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun getRoomDetailByRoomCode(roomCode: String): State<RoomDetailEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                roomApi.getRoomDetailByRoomCode(roomCode).let { roomDetailResponse ->
                    State.Success(roomDetailResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }
}