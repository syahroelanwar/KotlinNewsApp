package com.syahrul.kotlinnewsapp.Interface

import com.syahrul.kotlinnewsapp.Model.News
import com.syahrul.kotlinnewsapp.Model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsInterface {

    @get:GET("v2/sources?apiKey=7d56963359c04ef5b42c26b735125756")
    val sources: Call<News>

    @GET
    fun getNewsFromSource(@Url url:String):Call<NewsData>
}