package com.tdtd.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.getValue
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import com.tdtd.presentation.base.ui.BaseViewModel
import com.tdtd.presentation.entity.PresenterDeleteRoom
import com.tdtd.presentation.entity.PresenterRoomDetailEntity
import com.tdtd.presentation.mapper.toPresenterDeleteRoom
import com.tdtd.presentation.mapper.toPresenterRoomDetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getAllRoomsUseCase: GetAllRoomsUseCase
) : BaseViewModel() {

    private val _detailRoom = MutableLiveData<PresenterRoomDetailEntity>()
    val detailRoom: LiveData<PresenterRoomDetailEntity> get() = _detailRoom

    private val _deleteRoom = MutableLiveData<PresenterDeleteRoom>()

    fun getRoomDetailByRoomCode(roomCode: String) = viewModelScope.launch {
        getAllRoomsUseCase.invoke(roomCode).let { result ->
            showLoading()
            _detailRoom.value = result.getValue().toPresenterRoomDetailEntity()
        }
        hideLoading()
    }

    fun deleteParticipatedUserRoom(roomCode: String) = viewModelScope.launch {
        getAllRoomsUseCase.deleteRoom(roomCode).let { result ->
            showLoading()
            _deleteRoom.value = result.getValue().toPresenterDeleteRoom()
        }
        hideLoading()
    }
}