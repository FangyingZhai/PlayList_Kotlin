package com.zhai.fangying.playlist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zhai.fangying.playlist.R
import com.zhai.fangying.playlist.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "http://ws.audioscrobbler.com/2.0/?api_key=0685a4cd028881214d54c80c898c4020&format=json";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

}
