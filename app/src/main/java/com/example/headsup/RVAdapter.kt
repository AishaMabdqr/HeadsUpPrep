package com.example.headsup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (val itemList : ArrayList<CelebrityInfo.Info>) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var items = itemList[position]

        holder.itemView.apply {
            tvNames.text = items.name
            tvT1.text = items.taboo1
            tvT2.text = items.taboo2
            tvT3.text = items.taboo3
        }
    }

    override fun getItemCount()= itemList.size
}