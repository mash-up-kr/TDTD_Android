package com.tdtd.presentation.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(
    var title: String,
    //val type: String,
    var room_code: String,
    var share_url: String,
    var created_at: String,
    var is_bookmark: Boolean,
    var is_host: Boolean,
    //var comments : List<Comments>
) : Parcelable
