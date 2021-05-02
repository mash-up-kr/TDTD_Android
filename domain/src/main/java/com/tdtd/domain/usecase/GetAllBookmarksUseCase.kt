package com.tdtd.domain.usecase

import com.tdtd.domain.Result
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetAllBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(): Result<List<RoomEntity>> =
        bookmarkRepository.getUserBookmarkList()
}