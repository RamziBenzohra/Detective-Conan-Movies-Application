package com.rb.detectiveconan.movies.ui.fragments



sealed class MoviesUiEvent <T> (val data:T?=null){

    class OnToast <T>(text:String) :MoviesUiEvent<String>(data = text)

}
