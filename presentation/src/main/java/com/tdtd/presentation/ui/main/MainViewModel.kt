package com.tdtd.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.entity.MakeRoomEntity
import com.tdtd.domain.getValue
import com.tdtd.domain.usecase.GetAllBookmarksUseCase
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import com.tdtd.presentation.base.ui.BaseViewModel
import com.tdtd.presentation.entity.PresenterCreatedRoomCode
import com.tdtd.presentation.entity.Room
import com.tdtd.presentation.mapper.toPresenterCreated
import com.tdtd.presentation.mapper.toPresenterRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private val _makeRoom = MutableLiveData<PresenterCreatedRoomCode>()

    init {
        getUserRoomList()
    }

    fun getUserRoomList() = viewModelScope.launch {
        getAllRoomsUseCase.invoke().let { result ->
            showLoading()
            if (result.getValue().isEmpty()) {
                _emptyRoom.value = true
            } else {
                _emptyRoom.value = false
                _roomList.value = result.getValue().toPresenterRoom()
            }
        }
        hideLoading()
    }

    fun getUserBookmarkList() = viewModelScope.launch {
        getAllBookmarksUseCase.invoke().let { result ->
            showLoading()
            _roomList.value = result.getValue().toPresenterRoom()
        }
        hideLoading()
    }

    fun postCreateUserRoom(makeRoomEntity: MakeRoomEntity) = viewModelScope.launch {
        getAllRoomsUseCase.invoke(makeRoomEntity).let { result ->
            showLoading()
            _makeRoom.value = result.getValue().toPresenterCreated()
        }
        hideLoading()
    }
}