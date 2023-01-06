package com.rb.detectiveconan.movies.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SlidesEntity(

    var image:String?,var title:String,var link:String?,

    @PrimaryKey
    val id:String
):Parcelable

