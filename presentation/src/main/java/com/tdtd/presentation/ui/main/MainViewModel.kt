package com.tdtd.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.usecase.*
import com.tdtd.domain.util.State
import com.tdtd.domain.util.getValue
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
    private val getAllBookmarksUseCase: GetAllBookmarksUseCase,
    private val addBookMarkUseCase: AddBookMarkUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase,
    private val createUserRoomUseCase: CreateUserRoomUseCase,
    private val enterByRoomCodeUseCase: EnterByRoomCodeUseCase
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
                is State.Error -> {
                    throw result.exception
                }
                is State.Success -> {
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
                is State.Error -> throw result.exception
                is State.Success -> _roomList.value = result.getValue().toPresenterRoom()
            }
        }
    }

    fun postBookmarkByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = addBookMarkUseCase.invoke(roomCode)) {
                is State.Error -> throw result.exception
                is State.Success -> _favoriteRoom.value = result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun deleteBookmarkByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = deleteBookMarkUseCase.invoke(roomCode)) {
                is State.Error -> throw result.exception
                is State.Success -> _favoriteRoom.value = result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun postCreateUserRoom(makeRoomEntity: MakeRoomEntity) {
        viewModelScope.safeLaunch {
            when (val result = createUserRoomUseCase.invoke(makeRoomEntity)) {
                is State.Error -> throw result.exception
                is State.Success -> _makeRoom.value = result.getValue().toPresenterCreated()
            }
        }
    }

    fun postParticipateByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = enterByRoomCodeUseCase.invoke(roomCode)) {
                is State.Error -> throw result.exception
                is State.Success -> _inviteRoom.value = result.getValue().toPresenterDeleteRoom()
            }
        }
    }
}