package com.syahrul.kotlinnewsapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.syahrul.kotlinnewsapp.Adapter.ListNewsAdapter
import com.syahrul.kotlinnewsapp.Common.Common
import com.syahrul.kotlinnewsapp.Interface.NewsInterface
import com.syahrul.kotlinnewsapp.Model.NewsData
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source=""
    var webHotUrl:String?=""

    lateinit var dialog:AlertDialog
    lateinit var nService:NewsInterface
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        nService = Common.newsService
        dialog = SpotsDialog(this)
        swipe_list.setOnRefreshListener { loadNews(source,true) }
        dgLayout.setOnClickListener{
            val intent = Intent(baseContext,DetailActivity::class.java)
            intent.putExtra("webUrl",webHotUrl)
            startActivity(intent)
        }

        rv_list_news.setHasFixedSize(true)
        rv_list_news.layoutManager = LinearLayoutManager(this)

        if(intent != null){
            source = intent.getStringExtra("source")
            if(!source.isEmpty())
                loadNews(source,false)
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean){

        if(isRefreshed){
            dialog.show()
            nService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<NewsData>{
                    override fun onFailure(call: Call<NewsData>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                        dialog.dismiss()
                        swipe_list.isRefreshing = false

                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_img)

                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url
                        val removeFirstItem = response!!.body()!!.articles
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                        adapter.notifyDataSetChanged()
                        rv_list_news.adapter = adapter
                    }

                })
        }
        else{
            swipe_list.isRefreshing = true
            nService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<NewsData>{
                    override fun onFailure(call: Call<NewsData>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                        swipe_list.isRefreshing = false

                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_img)

                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url
                        val removeFirstItem = response!!.body()!!.articles
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                        adapter.notifyDataSetChanged()
                        rv_list_news.adapter = adapter
                    }

                })
        }
    }
}
