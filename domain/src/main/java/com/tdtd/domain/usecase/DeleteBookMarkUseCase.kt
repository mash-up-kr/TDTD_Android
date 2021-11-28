package com.tdtd.domain.usecase

import com.tdtd.domain.entity.DeleteRoomEntity
import com.tdtd.domain.repository.BookmarkRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class DeleteBookMarkUseCase(private val repository: BookmarkRepository) :
    BaseUseCase.WithParam<String, State<DeleteRoomEntity>> {

    override suspend fun invoke(param: String): State<DeleteRoomEntity> =
        repository.deleteBookmarkByRoomCode(param)
}