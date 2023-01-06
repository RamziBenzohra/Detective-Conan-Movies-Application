package com.rb.detectiveconan.movies.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CastEntity(
    var image:String?,var name:String,var carachter:String,
    @PrimaryKey
    val id:String
)