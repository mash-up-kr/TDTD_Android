package com.tdtd.voicepaper.di.module

import com.tdtd.data.repository.RoomRepositoryImpl
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
    abstract fun binsRoomRepository(
        roomRepositoryImpl: RoomRepositoryImpl
    ): RoomRepository
}
