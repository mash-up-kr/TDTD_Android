package com.tdtd.data.repository

import android.accounts.NetworkErrorException
import com.tdtd.data.api.BookmarkApi
import com.tdtd.data.mapper.toListRoomEntity
import com.tdtd.domain.IoDispatcher
import com.tdtd.domain.Result
import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkApi: BookmarkApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookmarkRepository {

    override suspend fun getUserBookmarkList(): Result<List<RoomEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.getUserBookmarkList().let { roomsResponse ->
                    Result.Success(roomsResponse.toListRoomEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun postBookmarkByRoomCode(roomCode: String): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.postBookmarkByRoomCode(roomCode).let { roomResponse->
                    Result.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun deleteBookmarkByRoomCode(roomCode: String): Result<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.deleteBookmarkByRoomCode(roomCode).let { roomResponse ->
                    Result.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}