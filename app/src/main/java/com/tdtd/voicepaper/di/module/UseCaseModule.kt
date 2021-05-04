package com.tdtd.voicepaper.di.module

import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.repository.BookmarkRepository
import com.tdtd.domain.repository.ReplyRepository
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.usecase.GetAllAdminUseCase
import com.tdtd.domain.usecase.GetAllBookmarksUseCase
import com.tdtd.domain.usecase.GetAllReplyUseCase
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

//hilt version upgrade 하면서 ViewModelScope로 변경할 것
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAllRoomsUseCase(
        repository: RoomRepository,
    ): GetAllRoomsUseCase = GetAllRoomsUseCase(
        repository
    )

    @Provides
    fun provideGetAllBookmarksUseCase(
        repository: BookmarkRepository
    ): GetAllBookmarksUseCase = GetAllBookmarksUseCase(
        repository
    )

    @Provides
    fun provideGetAllReplyUseCase(
        repository: ReplyRepository
    ): GetAllReplyUseCase = GetAllReplyUseCase(
        repository
    )

    @Provides
    fun provideGetAllAdminsUseCase(
        repository: AdminRepository
    ): GetAllAdminUseCase = GetAllAdminUseCase(
        repository
    )
}