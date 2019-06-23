@file:Suppress("DEPRECATION")

package com.sungbin.com.xvideo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import android.app.SearchManager
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast
import com.sungbin.com.xvideo.R
import com.sungbin.com.xvideo.adapter.VideoListAdapter
import com.sungbin.com.xvideo.dto.VideoListItem
import com.sungbin.com.xvideo.utils.HTML
import com.sungbin.com.xvideo.utils.Utils
import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private var items: ArrayList<VideoListItem>? = null
    private var FINISH_INTERVAL_TIME = 2000
    private var backPressedTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        items = ArrayList()

        val cut1 = "<div class=\"thumb\">"
        val cut2 = "</div>"
        val thumbnailUri = "https://img-hw.xvideos-cdn.com/videos/videopreview/"

        val test = HTML.get("https://xvideos.com").split("mozaique")[2]
            .split("subscriptions")[0].split("pagination")[0]

        /*
        <a href="/video43926665/11449435/0/outdoor_blowjob_and_fuck">
        <img src="https://static-egc.xvideos-cdn.com/img/lightbox/lightbox-blank.gif"
        data-src="https://img-hw.xvideos-cdn.com/videos/thumbs169/d9/f3/3d/d9f33d7f0a5c58e0495cc79d6a8c5bfa/d9f33d7f0a5c58e0495cc79d6a8c5bfa.16.jpg"
        data-idcdn="2" data-videoid="43926665" id="pic_43926665" /></a>
         */

        for(i in 1 until test.split(cut1).size){
            val cash = test.split(cut1)[i].split(cut2)[0]
            val main = cash.split("href=\"")[1].split("\">")[0]
            val cutInt = cash.split("href=\"")[1].split("\"")[0].split("/").size - 1
            val pic = cash.split("data-src=\"")[1].split("\"")[0]
            val name = cash.split("href=\"")[1].split("\"")[0].split("/")[cutInt]
            val cutInt2 = pic.split("thumbs169/")[1].split("/").size - 1
            val replaceStr = pic.split("thumbs169/")[1].split("/")[cutInt2]
            val thumbnail = (thumbnailUri + pic.split("thumbs169/")[1].replace(replaceStr, "") + "_169.mp4")
                .replace("/_169.mp4", "_169.mp4")
            items!!.add(VideoListItem(name, pic, thumbnail, main))
        }

        val adapter = VideoListAdapter(items, this)
        video_list.adapter = adapter
        video_list.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.queryHint = getString(R.string.input_search_keyword)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(str: String?): Boolean {
                    Utils.toast(applicationContext, str.toString())
                    return false
                }

                override fun onQueryTextChange(str: String?): Boolean {
                    return false
                }

            })
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.setIconifiedByDefault(true)

        }
        return true
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        }
        else {
            backPressedTime = tempTime.toInt()
            Utils.toast(applicationContext,
                "뒤로가기를 한번 더 누르시면 앱이 완전히 종료됩니다.",
                FancyToast.LENGTH_SHORT, FancyToast.WARNING)
        }
    }

}
