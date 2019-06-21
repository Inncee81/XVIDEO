package com.sungbin.com.xvideo.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sungbin.com.xvideo.R
import com.sungbin.com.xvideo.dto.VideoListItem
import java.io.ByteArrayOutputStream


class VideoListAdapter(private val list: ArrayList<VideoListItem>?,
                          private val act: Activity
) :
    RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.video_list, viewGroup, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewholder: VideoViewHolder, position: Int) {
        val name = list!![position].name

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun getItem(position: Int): VideoListItem {
        return list!![position]
    }

}
