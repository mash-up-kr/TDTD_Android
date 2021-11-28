package com.tdtd.domain.usecase

import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.repository.BookmarkRepository
import com.tdtd.domain.util.BaseUseCase
import com.tdtd.domain.util.State

class GetAllBookmarksUseCase(private val repository: BookmarkRepository) :
    BaseUseCase.NoParam<State<List<RoomEntity>>> {

    override suspend fun invoke(): State<List<RoomEntity>> = repository.getUserBookmarkList()
}