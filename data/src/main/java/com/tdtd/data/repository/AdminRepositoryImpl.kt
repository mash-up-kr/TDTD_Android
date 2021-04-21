package com.tdtd.data.repository

import android.accounts.NetworkErrorException
import com.tdtd.data.api.AdminApi
import com.tdtd.domain.Result
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.repository.AdminRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdminRepository {
    override suspend fun deleteRoom(roomCode: String): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteRoom(roomCode).let {
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

    override suspend fun getSharedRoomUrl(roomCode: String) =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.getSharedRoomUrl(roomCode).let {
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

    override suspend fun deleteOtherCommentByAdmin(): Result<RoomsEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                adminApi.deleteOtherCommentByAdmin().let {
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