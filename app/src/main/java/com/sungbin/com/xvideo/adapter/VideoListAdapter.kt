package com.sungbin.com.xvideo.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.net.Uri
import android.os.Build
import android.support.annotation.NonNull
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.jzvd.JzvdStd
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sungbin.com.xvideo.R
import com.sungbin.com.xvideo.dto.VideoListItem
import com.sungbin.com.xvideo.utils.HTML
import com.sungbin.com.xvideo.utils.Utils
import java.io.ByteArrayOutputStream


class VideoListAdapter(private val list: ArrayList<VideoListItem>?,
                       private val act: Activity
) :
    RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.video_list, viewGroup, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewholder: VideoViewHolder, position: Int) {
        val name = list!![position].name
        val thumbPic = list[position].link
        val thumbMp4 = list[position].thumbnail
        val main = "https://xvideos.com" + list[position].main
        Glide.with(act).load(thumbPic)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(viewholder.thumbnail)
        viewholder.thumbnail.setOnLongClickListener { //썸네일
            val dialog = AlertDialog.Builder(act)
            val view = JzvdStd(act)
            view.setUp(thumbMp4, name)
            view.startVideo()
            dialog.setView(view)
            dialog.show()
            return@setOnLongClickListener false
        }
        viewholder.thumbnail.setOnClickListener { //영상 플레이
            val dialog = AlertDialog.Builder(act)
            val view = JzvdStd(act)
            val link = convertVideoLinkToVideoURI(main)
            view.setUp(link, name)
            view.startVideo()
            dialog.setView(view)
            dialog.setPositiveButton("크롬으로 열기", DialogInterface.OnClickListener { _, _ ->
                val builder = CustomTabsIntent.Builder()
                builder.setStartAnimations(act, android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                builder.setExitAnimations(act, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(act, Uri.parse(link));
            })
            dialog.show()
        }
        viewholder.title.text = name
        viewholder.title.setOnLongClickListener {
            Utils.copy(act, name!!)
            return@setOnLongClickListener false
        }
    }

    private fun convertVideoLinkToVideoURI(url: String): String{
        val html = HTML.get(url)
        val data = html.split("html5player.setVideoUrlHigh('")[1]
            .split("');")[0]
        return data
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
