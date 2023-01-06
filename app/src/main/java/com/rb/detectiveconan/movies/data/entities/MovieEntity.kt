package com.rb.detectiveconan.movies.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MovieEntity(
    var title:String,
    var description:String?,
    var thumbnail:String?,
    var rating:String,
    var streaminglink:String?,
    @PrimaryKey
    val id:String
):Parcelable

