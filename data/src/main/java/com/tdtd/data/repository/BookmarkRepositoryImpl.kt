package com.tdtd.data.repository

import android.accounts.NetworkErrorException
import com.tdtd.data.api.BookmarkApi
import com.tdtd.domain.Result
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkApi: BookmarkApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookmarkRepository {

    override suspend fun getUserBookmarkList(): Result<List<RoomEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.getUserBookmarkList().let {
                    if (it is Result.Success) {
                        Result.Success(it.data.map { roomResponse ->
                            roomResponse.toEntity()
                        })
                    } else {
                        Result.Error(NetworkErrorException())
                    }
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun postBookmarkByRoomCode(roomCode: String): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.postBookmarkByRoomCode(roomCode).let {
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

    override suspend fun deleteBookmarkByRoomCode(roomCode: String): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.deleteBookmarkByRoomCode(roomCode).let {
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