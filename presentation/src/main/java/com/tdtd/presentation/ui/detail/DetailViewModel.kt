package com.tdtd.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.Result
import com.tdtd.domain.entity.ReplyUserCommentWithFileEntity
import com.tdtd.domain.getValue
import com.tdtd.domain.usecase.GetAllAdminUseCase
import com.tdtd.domain.usecase.GetAllReplyUseCase
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import com.tdtd.presentation.base.ui.BaseViewModel
import com.tdtd.presentation.entity.PresenterDeleteRoom
import com.tdtd.presentation.entity.PresenterRoomDetailEntity
import com.tdtd.presentation.entity.PresenterRoomUrlEntity
import com.tdtd.presentation.entity.Rooms
import com.tdtd.presentation.mapper.toPresenterDeleteRoom
import com.tdtd.presentation.mapper.toPresenterRoomDetailEntity
import com.tdtd.presentation.mapper.toPresenterRoomUrlEntity
import com.tdtd.presentation.mapper.toRooms
import com.tdtd.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val getAllReplyUseCase: GetAllReplyUseCase,
    private val getAllAdminUseCase: GetAllAdminUseCase
) : BaseViewModel() {

    private val _detailRoom = MutableLiveData<PresenterRoomDetailEntity>()
    val detailRoom: LiveData<PresenterRoomDetailEntity> get() = _detailRoom

    private val _deleteRoom = MutableLiveData<PresenterDeleteRoom>()

    private val _replyValue = MutableLiveData<PresenterDeleteRoom>()

    private val _deleteComment = MutableLiveData<PresenterRoomDetailEntity>()

    private val _reportComment = MutableLiveData<PresenterDeleteRoom>()

    private val _deleteHostRoom = MutableLiveData<Rooms>()

    private val _deleteCommentsByHost = MutableLiveData<Rooms>()

    private val _sharedUrl = MutableLiveData<PresenterRoomUrlEntity>()

    private val _notMineEvent = SingleLiveEvent<Unit>()
    val notMineEvent: LiveData<Unit> get() = _notMineEvent

    private val _alreadyReportEvent = SingleLiveEvent<Unit>()
    val alreadyReportEvent: LiveData<Unit> get() = _alreadyReportEvent

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

    fun postReplyUserComment(
        roomCode: String,
        replyUserCommentWithFileEntity: ReplyUserCommentWithFileEntity
    ) = viewModelScope.launch {
        getAllReplyUseCase.invoke(roomCode, replyUserCommentWithFileEntity).let { result ->
            showLoading()
            _replyValue.value = result.getValue().toPresenterDeleteRoom()
        }
        hideLoading()
    }

    fun deleteReplyUserComment(commentId: Long) = viewModelScope.launch {
        getAllReplyUseCase.invoke(commentId).let { result ->
            showLoading()
            if (result is Result.Success) {
                _deleteComment.value = result.data.toPresenterRoomDetailEntity()
            } else {
                _notMineEvent.call()
            }
        }
        hideLoading()
    }

    fun postReportUserByCommentId(commentId: Long) = viewModelScope.launch {
        getAllReplyUseCase.postReportUserByCommentId(commentId).let { result ->
            showLoading()
            if (result is Result.Success) {
                _reportComment.value = result.getValue().toPresenterDeleteRoom()
            } else {
                _alreadyReportEvent.call()
            }
        }
        hideLoading()
    }

    // "Request method 'DELETE' not supported"
    fun deleteRoomByHost(roomCode: String) = viewModelScope.launch {
        getAllAdminUseCase.invoke(roomCode).let { result ->
            showLoading()
            _deleteHostRoom.value = result.getValue().toRooms()
        }
        hideLoading()
    }

    fun deleteOtherCommentByAdmin(commentId: Long) = viewModelScope.launch {
        getAllAdminUseCase.invoke(commentId).let { result ->
            showLoading()
            _deleteCommentsByHost.value = result.getValue().toRooms()
        }
        hideLoading()
    }

    fun getSharedRoomUrl(roomCode: String) = viewModelScope.launch {
        getAllAdminUseCase.getSharedRoomUrl(roomCode).let { result ->
            showLoading()
            _sharedUrl.value = result.getValue().toPresenterRoomUrlEntity()
        }
        hideLoading()
    }
}