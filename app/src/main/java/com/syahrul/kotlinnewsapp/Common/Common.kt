package com.syahrul.kotlinnewsapp.Common

import com.syahrul.kotlinnewsapp.Interface.NewsInterface
import com.syahrul.kotlinnewsapp.Remote.RetrofitClient

object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_K = "7d56963359c04ef5b42c26b735125756"

    val newsService: NewsInterface
    get()=RetrofitClient.getClient(BASE_URL).create(NewsInterface::class.java)

    fun getNewsAPI(source:String): String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_K)
            .toString()
        return apiUrl
    }
}