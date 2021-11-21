package com.tdtd.data.repository

import com.tdtd.data.api.BookmarkApi
import com.tdtd.data.mapper.toListRoomEntity
import com.tdtd.data.util.IoDispatcher
import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.repository.BookmarkRepository
import com.tdtd.domain.util.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkApi: BookmarkApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookmarkRepository {

    override suspend fun getUserBookmarkList(): State<List<RoomEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.getUserBookmarkList().let { roomsResponse ->
                    State.Success(roomsResponse.toListRoomEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun postBookmarkByRoomCode(roomCode: String): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.postBookmarkByRoomCode(roomCode).let { roomResponse ->
                    State.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }

    override suspend fun deleteBookmarkByRoomCode(roomCode: String): State<DeleteRoomEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                bookmarkApi.deleteBookmarkByRoomCode(roomCode).let { roomResponse ->
                    State.Success(roomResponse.toEntity())
                }
            } catch (e: Exception) {
                State.Error(e)
            }
        }
}