package com.tdtd.voicepaper.di.module

import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.repository.BookmarkRepository
import com.tdtd.domain.repository.ReplyRepository
import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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
    fun provideModifyRoomNameUseCase(
        repository: AdminRepository
    ): ModifyRoomNameUseCase = ModifyRoomNameUseCase(
        repository
    )

    @Provides
    fun provideDeleteRoomByAdminUseCase(
        repository: AdminRepository
    ): DeleteRoomByAdminUseCase = DeleteRoomByAdminUseCase(
        repository
    )

    @Provides
    fun provideDeleteCommentByAdminUseCase(
        repository: AdminRepository
    ): DeleteCommentByAdminUseCase = DeleteCommentByAdminUseCase(
        repository
    )

    @Provides
    fun provideGetSharedRoomUseCase(
        repository: AdminRepository
    ): GetShareRoomUseCase = GetShareRoomUseCase(
        repository
    )

    @Provides
    fun provideGetRoomDetailUseCase(
        repository: RoomRepository
    ): GetRoomDetailUseCase = GetRoomDetailUseCase(
        repository
    )

    @Provides
    fun provideDeleteRoomByUserUseCase(
        repository: RoomRepository
    ): DeleteRoomByUserUseCase = DeleteRoomByUserUseCase(
        repository
    )

    @Provides
    fun provideADeleteCommentByUserUseCase(
        repository: ReplyRepository
    ): DeleteCommentByUserUseCase = DeleteCommentByUserUseCase(
        repository
    )

    @Provides
    fun provideReportCommentUseCase(
        repository: ReplyRepository
    ): ReportCommentUseCase = ReportCommentUseCase(
        repository
    )

    @Provides
    fun provideAddBookMarkUseCase(
        repository: BookmarkRepository
    ): AddBookMarkUseCase = AddBookMarkUseCase(
        repository
    )

    @Provides
    fun provideCreateUserRoomUseCase(
        repository: RoomRepository
    ): CreateUserRoomUseCase = CreateUserRoomUseCase(
        repository
    )

    @Provides
    fun provideDeleteBookMarkUseCase(
        repository: BookmarkRepository
    ): DeleteBookMarkUseCase = DeleteBookMarkUseCase(
        repository
    )

    @Provides
    fun provideEnterByRoomCodeUseCase(
        repository: RoomRepository
    ): EnterByRoomCodeUseCase = EnterByRoomCodeUseCase(
        repository
    )
}