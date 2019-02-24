package net.azarquiel.darksky.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*
import net.azarquiel.darksky.model.Data
import net.azarquiel.darksky.util.Util

class CustomAdapter(val context: Context, val layout: Int, val dataList: List<Data>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Data){
            itemView.tvPronoRow.text = dataItem.summary
            itemView.tvfecha.text = Util.timeSpanToDate(dataItem.time)
            itemView.tvMaxRow.text = "${Util.farToCel(dataItem.temperatureMax)}º"
            itemView.tvMinRow.text = "${Util.farToCel(dataItem.temperatureMin)}º"
            itemView.tvPopRow.text = "${dataItem.precipProbability}%"
            itemView.tag = dataItem

            Picasso.with(context).load("https://darksky.net/images/weather-icons/${dataItem.icon}.png").into(itemView.ivRow)
        }

    }
}