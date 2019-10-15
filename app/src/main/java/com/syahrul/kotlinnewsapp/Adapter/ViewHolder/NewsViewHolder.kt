package com.syahrul.kotlinnewsapp.Adapter.ViewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.syahrul.kotlinnewsapp.Interface.ItemClickListener
import kotlinx.android.synthetic.main.news_list.view.*

class NewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener:ItemClickListener
    var news_title = itemView.title_news

    init{
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!,adapterPosition)
    }
}