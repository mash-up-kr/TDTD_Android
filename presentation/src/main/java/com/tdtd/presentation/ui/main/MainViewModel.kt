package com.tdtd.presentation.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdtd.domain.Result
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.domain.usecase.GetAllRoomsUseCase
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private var getAllRoomsUseCase: GetAllRoomsUseCase) : ViewModel() {
    var result = MutableLiveData<List<RoomEntity>>()

    fun getRooms() {
        viewModelScope.launch {
            result.value = getAllRoomsUseCase().let {
                if (it is Result.Success) {
                    it.data.rooms
                } else {
                    emptyList<RoomEntity>()
                }
            }
        }
    }
}
