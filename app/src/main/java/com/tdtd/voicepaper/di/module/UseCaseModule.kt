package com.tdtd.voicepaper.di.module

import com.tdtd.domain.repository.RoomRepository
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
//hilt version upgrade 하면서 ViewModelScope로 변경할 것
@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAllRoomsUseCase(
        repository: RoomRepository,
    ): GetAllRoomsUseCase = GetAllRoomsUseCase(
        repository
    )
}