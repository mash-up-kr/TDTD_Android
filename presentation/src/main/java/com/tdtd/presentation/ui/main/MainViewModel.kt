package com.tdtd.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.Result
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.getValue
import com.tdtd.domain.usecase.GetAllBookmarksUseCase
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import com.tdtd.presentation.base.ui.BaseViewModel
import com.tdtd.presentation.entity.PresenterCreatedRoomCode
import com.tdtd.presentation.entity.PresenterDeleteRoom
import com.tdtd.presentation.entity.Room
import com.tdtd.presentation.mapper.toPresenterCreated
import com.tdtd.presentation.mapper.toPresenterDeleteRoom
import com.tdtd.presentation.mapper.toPresenterRoom
import com.tdtd.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getAllBookmarksUseCase: GetAllBookmarksUseCase
) : BaseViewModel() {

    private val _roomList = MutableLiveData<List<Room>>()
    val roomList: LiveData<List<Room>> get() = _roomList

    private val _emptyRoom = MutableLiveData<Boolean>()
    val emptyRoom: LiveData<Boolean> get() = _emptyRoom

    private val _makeRoom = SingleLiveEvent<PresenterCreatedRoomCode>()
    val makeRoom: LiveData<PresenterCreatedRoomCode> get() = _makeRoom

    private val _favoriteRoom = MutableLiveData<PresenterDeleteRoom>()

    private val _inviteRoom = MutableLiveData<PresenterDeleteRoom>()

    fun getUserRoomList() {
        viewModelScope.safeLaunch {
            when (val result = getAllRoomsUseCase.invoke()) {
                is Result.Error -> {
                    throw result.exception
                }
                is Result.Success -> {
                    if (result.getValue().isEmpty()) {
                        _emptyRoom.value = true
                    } else {
                        _emptyRoom.value = false
                        _roomList.value = result.getValue().toPresenterRoom()
                    }
                }
            }
        }
    }

    fun getUserBookmarkList() {
        viewModelScope.safeLaunch {
            when (val result = getAllBookmarksUseCase.invoke()) {
                is Result.Error -> throw result.exception
                is Result.Success -> _roomList.value = result.getValue().toPresenterRoom()
            }
        }
    }

    fun postBookmarkByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getAllBookmarksUseCase.postBookmark(roomCode)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _favoriteRoom.postValue(
                    result.getValue().toPresenterDeleteRoom()
                )
            }
        }
    }

    fun deleteBookmarkByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getAllBookmarksUseCase.deleteBookmark(roomCode)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _favoriteRoom.value = result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun postCreateUserRoom(makeRoomEntity: MakeRoomEntity) {
        viewModelScope.safeLaunch {
            when (val result = getAllRoomsUseCase.invoke(makeRoomEntity)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _makeRoom.value = result.getValue().toPresenterCreated()
            }
        }
    }

    fun postParticipateByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getAllRoomsUseCase.postParticipateByRoomCode(roomCode)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _inviteRoom.value = result.getValue().toPresenterDeleteRoom()
            }
        }
    }
}