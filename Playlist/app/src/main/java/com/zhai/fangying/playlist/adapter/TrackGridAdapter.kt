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

class TrackGridAdapter(private val data: ArrayList<Track>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.album_item, parent, false)

        if(view.tag == null) {
            val viewHolder = ViewHolder()
            viewHolder.imageView =view.findViewById(R.id.image_view)
            viewHolder.textView1 =view.findViewById(R.id.text1)
            viewHolder.textView2 =view.findViewById(R.id.text2)
            view.tag = viewHolder
        }

        val viewHolder = view.tag as ViewHolder

        val item : Track = data[position]

        Glide.with(view.context).load(item.image).into(viewHolder.imageView);
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
        lateinit var imageView : ImageView
        lateinit var textView1 : TextView
        lateinit var textView2 : TextView
    }
}