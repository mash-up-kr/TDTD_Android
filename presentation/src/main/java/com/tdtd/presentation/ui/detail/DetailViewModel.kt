package com.tdtd.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.Result
import com.tdtd.domain.getValue
import com.tdtd.domain.usecase.GetAllAdminUseCase
import com.tdtd.domain.usecase.GetAllReplyUseCase
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import com.tdtd.presentation.base.ui.BaseViewModel
import com.tdtd.presentation.entity.PresenterDeleteRoom
import com.tdtd.presentation.entity.PresenterReplyUserEntity
import com.tdtd.presentation.entity.PresenterRoomDetailEntity
import com.tdtd.presentation.entity.PresenterRoomUrlEntity
import com.tdtd.presentation.mapper.toPresenterDeleteRoom
import com.tdtd.presentation.mapper.toPresenterReplyUserEntity
import com.tdtd.presentation.mapper.toPresenterRoomDetailEntity
import com.tdtd.presentation.mapper.toPresenterRoomUrlEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
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

    private val _replyValue = MutableLiveData<PresenterReplyUserEntity>()

    private val _deleteComment = MutableLiveData<PresenterDeleteRoom>()

    private val _reportComment = MutableLiveData<PresenterDeleteRoom>()

    private val _deleteHostRoom = MutableLiveData<PresenterDeleteRoom>()

    private val _deleteCommentsByHost = MutableLiveData<PresenterDeleteRoom>()

    private val _sharedUrl = MutableLiveData<PresenterRoomUrlEntity>()
    val sharedUrl: LiveData<PresenterRoomUrlEntity> get() = _sharedUrl

    fun getRoomDetailByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getAllRoomsUseCase.invoke(roomCode)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _detailRoom.value =
                    result.getValue().toPresenterRoomDetailEntity()
            }
        }
    }

    fun deleteParticipatedUserRoom(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getAllRoomsUseCase.deleteRoom(roomCode)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _deleteRoom.value = result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun postReplyUserComment(
        roomCode: String,
        params: List<MultipartBody.Part>
    ) {
        viewModelScope.safeLaunch {
            when (val result = getAllReplyUseCase.invoke(roomCode, params)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _replyValue.value =
                    result.getValue().toPresenterReplyUserEntity()
            }
        }
    }

    fun deleteReplyUserComment(commentId: Long) {
        viewModelScope.safeLaunch {
            when (val result = getAllReplyUseCase.invoke(commentId)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _deleteComment.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun postReportUserByCommentId(commentId: Long) {
        viewModelScope.safeLaunch {
            when (val result = getAllReplyUseCase.postReportUserByCommentId(commentId)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _reportComment.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun deleteRoomByHost(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getAllAdminUseCase.invoke(roomCode)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _deleteHostRoom.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun deleteOtherCommentByAdmin(commentId: Long) {
        viewModelScope.safeLaunch {
            when (val result = getAllAdminUseCase.invoke(commentId)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _deleteCommentsByHost.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun getSharedRoomUrl(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getAllAdminUseCase.getSharedRoomUrl(roomCode)) {
                is Result.Error -> throw result.exception
                is Result.Success -> _sharedUrl.value = result.getValue().toPresenterRoomUrlEntity()
            }
        }
    }
}