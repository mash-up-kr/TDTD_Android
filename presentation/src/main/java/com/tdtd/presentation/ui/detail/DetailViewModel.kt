package com.tdtd.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.entity.ModifyRoomNameEntity
import com.tdtd.domain.usecase.*
import com.tdtd.domain.util.State
import com.tdtd.domain.util.getValue
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
    private val getAllReplyUseCase: GetAllReplyUseCase,
    private val modifyRoomNameUseCase: ModifyRoomNameUseCase,
    private val deleteRoomByAdminUseCase: DeleteRoomByAdminUseCase,
    private val deleteCommentByAdminUseCase: DeleteCommentByAdminUseCase,
    private val getShareRoomUseCase: GetShareRoomUseCase,
    private val getRoomDetailUseCase: GetRoomDetailUseCase,
    private val deleteRoomByUserUseCase: DeleteRoomByUserUseCase,
    private val deleteCommentByUserUseCase: DeleteCommentByUserUseCase,
    private val reportCommentUseCase: ReportCommentUseCase
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

    private val _modifyRoomNameByHost = MutableLiveData<PresenterDeleteRoom>()

    fun getRoomDetailByRoomCode(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getRoomDetailUseCase.invoke(roomCode)) {
                is State.Error -> throw result.exception
                is State.Success -> _detailRoom.value =
                    result.getValue().toPresenterRoomDetailEntity()
            }
        }
    }

    fun deleteParticipatedUserRoom(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = deleteRoomByUserUseCase.invoke(roomCode)) {
                is State.Error -> throw result.exception
                is State.Success -> _deleteRoom.value = result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun postReplyUserComment(
        roomCode: String,
        params: List<MultipartBody.Part>
    ) {
        viewModelScope.safeLaunch {
            when (val result = getAllReplyUseCase.invoke(roomCode, params)) {
                is State.Error -> throw result.exception
                is State.Success -> _replyValue.value =
                    result.getValue().toPresenterReplyUserEntity()
            }
        }
    }

    fun deleteReplyUserComment(commentId: Long) {
        viewModelScope.safeLaunch {
            when (val result = deleteCommentByUserUseCase.invoke(commentId)) {
                is State.Error -> throw result.exception
                is State.Success -> _deleteComment.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun postReportUserByCommentId(commentId: Long) {
        viewModelScope.safeLaunch {
            when (val result = reportCommentUseCase.invoke(commentId)) {
                is State.Error -> throw result.exception
                is State.Success -> _reportComment.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun deleteRoomByHost(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = deleteRoomByAdminUseCase.invoke(roomCode)) {
                is State.Error -> throw result.exception
                is State.Success -> _deleteHostRoom.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun deleteOtherCommentByAdmin(commentId: Long) {
        viewModelScope.safeLaunch {
            when (val result = deleteCommentByAdminUseCase.invoke(commentId)) {
                is State.Error -> throw result.exception
                is State.Success -> _deleteCommentsByHost.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }

    fun getSharedRoomUrl(roomCode: String) {
        viewModelScope.safeLaunch {
            when (val result = getShareRoomUseCase.invoke(roomCode)) {
                is State.Error -> throw result.exception
                is State.Success -> _sharedUrl.value = result.getValue().toPresenterRoomUrlEntity()
            }
        }
    }

    fun modifyRoomNameByHost(roomCode: String, roomName: ModifyRoomNameEntity) {
        viewModelScope.safeLaunch {
            when (val result = modifyRoomNameUseCase.invoke(roomCode, roomName)) {
                is State.Error -> throw result.exception
                is State.Success -> _modifyRoomNameByHost.value =
                    result.getValue().toPresenterDeleteRoom()
            }
        }
    }
}