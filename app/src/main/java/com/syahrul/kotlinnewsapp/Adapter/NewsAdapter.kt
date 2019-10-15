package com.syahrul.kotlinnewsapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.syahrul.kotlinnewsapp.Adapter.ViewHolder.NewsViewHolder
import com.syahrul.kotlinnewsapp.Interface.ItemClickListener
import com.syahrul.kotlinnewsapp.ListNews
import com.syahrul.kotlinnewsapp.Model.News
import com.syahrul.kotlinnewsapp.R

class NewsAdapter(private val context:Context,private val news:News):RecyclerView.Adapter<NewsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_list,parent,false)
        return NewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return news.sources!!.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder!!.news_title.text = news.sources!![position].name
        holder.setItemClickListener(object : ItemClickListener{

            override fun onClick(view: View, position: Int) {
               val intent = Intent(context,ListNews::class.java)
                intent.putExtra("source",news.sources!![position].id)
                context.startActivity(intent)
            }
        })
    }

}