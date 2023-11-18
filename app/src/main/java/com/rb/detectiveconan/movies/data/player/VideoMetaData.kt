package com.rb.detectiveconan.movies.data.player

import android.net.Uri

interface VideoMetaData {
    fun getVideoUri (path: String) : Uri
}