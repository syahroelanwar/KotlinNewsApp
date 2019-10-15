package com.syahrul.kotlinnewsapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.syahrul.kotlinnewsapp.Adapter.NewsAdapter
import com.syahrul.kotlinnewsapp.Common.Common
import com.syahrul.kotlinnewsapp.Interface.NewsInterface
import com.syahrul.kotlinnewsapp.Model.News
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var nService: NewsInterface
    lateinit var adapter: NewsAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)
        nService = Common.newsService

        swipe.setOnRefreshListener{
            loadNews(true)
        }

        rv_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rv_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)
        loadNews(false)
    }

    private fun loadNews(isRefresh: Boolean) {

        if(!isRefresh){
            val cache = Paper.book().read<String>("cache")
            if(cache != null && !cache.isBlank() && cache != "null"){
                val news = Gson().fromJson<News>(cache,News::class.java)
                adapter = NewsAdapter(baseContext,news)
                adapter.notifyDataSetChanged()
                rv_news.adapter = adapter
            }else{
                dialog.show()
                nService.sources.enqueue(object:retrofit2.Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        Toast.makeText(baseContext,"Gagal",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        adapter = NewsAdapter(baseContext, response.body()!!)
                        adapter.notifyDataSetChanged()
                        rv_news.adapter = adapter

                        Paper.book().write("cache",Gson().toJson(response.body()!!))
                        dialog.dismiss()
                    }

                })
            }
        }
        else{
            swipe.isRefreshing = true
            nService.sources.enqueue(object:retrofit2.Callback<News>{
                override fun onFailure(call: Call<News>, t: Throwable) {
                    Toast.makeText(baseContext,"Gagal",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<News>, response: Response<News>) {
                    adapter = NewsAdapter(baseContext, response.body()!!)
                    adapter.notifyDataSetChanged()
                    rv_news.adapter = adapter

                    Paper.book().write("cache",Gson().toJson(response.body()!!))

                    swipe.isRefreshing = false
                }

            })
        }
    }
}
