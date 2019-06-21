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
import com.sungbin.com.xvideo.R
import com.sungbin.com.xvideo.adapter.VideoListAdapter
import com.sungbin.com.xvideo.dto.VideoListItem
import com.sungbin.com.xvideo.utils.Utils
import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private var items: ArrayList<VideoListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        items = ArrayList()
        items!!.add(VideoListItem("테스트", "테스트"))
        items!!.add(VideoListItem("테스트2", "테스트2"))
        items!!.add(VideoListItem("테스트3", "테스트3"))
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

}
