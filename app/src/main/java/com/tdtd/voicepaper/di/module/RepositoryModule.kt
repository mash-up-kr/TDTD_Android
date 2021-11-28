package com.tdtd.voicepaper.di.module

import com.tdtd.data.repository.AdminRepositoryImpl
import com.tdtd.data.repository.BookmarkRepositoryImpl
import com.tdtd.data.repository.ReplyRepositoryImpl
import com.tdtd.data.repository.RoomRepositoryImpl
import com.tdtd.domain.repository.AdminRepository
import com.tdtd.domain.repository.BookmarkRepository
import com.tdtd.domain.repository.ReplyRepository
import com.tdtd.domain.repository.RoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsRoomRepository(
        roomRepositoryImpl: RoomRepositoryImpl
    ): RoomRepository

    @Singleton
    @Binds
    abstract fun bindsReplyRepository(
        replyRepositoryImpl: ReplyRepositoryImpl
    ): ReplyRepository

    @Singleton
    @Binds
    abstract fun bindsBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository

    @Singleton
    @Binds
    abstract fun bindsAdminRepository(
        adminRepositoryImpl: AdminRepositoryImpl
    ): AdminRepository

}
