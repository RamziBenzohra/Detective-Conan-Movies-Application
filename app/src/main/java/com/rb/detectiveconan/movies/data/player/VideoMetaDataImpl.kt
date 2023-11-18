package com.rb.detectiveconan.movies.data.player

import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class VideoMetaDataImpl  : VideoMetaData {

    override fun getVideoUri(path: String): Uri {

        return  Uri.parse(path)
       // return  Uri.parse("https://firebasestorage.googleapis.com/v0/b/detective-conan-movies-b8200.appspot.com/o/Movies%2FDetective%20Conan%20Movie%201.1080p.mp4?alt=media&token=10b24bf8-af78-45b0-981b-947d44257d4c")
    }
}