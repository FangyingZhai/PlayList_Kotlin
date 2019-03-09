package com.zhai.fangying.playlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zhai.fangying.playlist.R
import com.zhai.fangying.playlist.model.Track

class TrackListAdapter(private val data: List<Track>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.playlist_item, parent, false)

        if(view.tag == null) {
            val viewHolder = ViewHolder()
            viewHolder.textView1 =view.findViewById(R.id.text1)
            viewHolder.textView2 =view.findViewById(R.id.text2)
            view.tag = viewHolder
        }

        val viewHolder = view.tag as ViewHolder

        val item : Track = data[position]

        viewHolder.textView1.text = item.name
        viewHolder.textView2.text = item.artist

        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return data.size
    }

    class ViewHolder {
        lateinit var textView1 : TextView
        lateinit var textView2 : TextView
    }
}