package com.syahrul.kotlinnewsapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.syahrul.kotlinnewsapp.Adapter.ViewHolder.ListNewsViewHolder
import com.syahrul.kotlinnewsapp.Common.ISO8601Parser
import com.syahrul.kotlinnewsapp.DetailActivity
import com.syahrul.kotlinnewsapp.Interface.ItemClickListener
import com.syahrul.kotlinnewsapp.Model.Article
import com.syahrul.kotlinnewsapp.R
import java.text.ParseException
import java.util.*

class ListNewsAdapter(val articleList:MutableList<Article>,private val context:Context):RecyclerView.Adapter<ListNewsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemview = inflater.inflate(R.layout.news_list_item,parent,false)
        return ListNewsViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.artc_img)

        if(articleList[position].title!!.length> 65){
            holder.artc_title.text = articleList[position].title!!.substring(0,65)+"..."
        }else{
            holder.artc_title.text = articleList[position].title!!
        }

        if(articleList[position].publishedAt != null){
            var date:Date?=null
            try {
                date = ISO8601Parser.parse(articleList[position].publishedAt!!)
            }catch (ex:ParseException){
                ex.printStackTrace()
            }
            holder.artc_time.setReferenceTime(date!!.time)
        }

        holder.setItemClickListener(object :ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context,DetailActivity::class.java)
                intent.putExtra("webUrl",articleList[position].url)
                context.startActivity(intent)
            }

        })
    }

}