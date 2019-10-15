package com.syahrul.kotlinnewsapp.Adapter.ViewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.syahrul.kotlinnewsapp.Interface.ItemClickListener
import kotlinx.android.synthetic.main.news_list_item.view.*

class ListNewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
    override fun onClick(v: View) {
       itemClickListener.onClick(v,adapterPosition)
    }

    fun setItemClickListener (itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    private lateinit var itemClickListener: ItemClickListener

    var artc_title = itemView.title_artc
    var artc_time = itemView.time_artc
    var artc_img = itemView.img_artc

    init{
        itemView.setOnClickListener(this)
    }
}