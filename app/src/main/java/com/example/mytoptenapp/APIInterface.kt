package com.example.mytoptenapp

import com.example.mytoptenapp.Feed
import retrofit2.Call
import retrofit2.http.GET

interface FeedAPIInterface {

    @get:GET("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
    val feed: Call<Feed?>?

    companion object {
        const val BASE_URL = "http://ax.itunes.apple.com/"
    }
}