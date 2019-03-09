package com.zhai.fangying.playlist.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.zhai.fangying.playlist.fragment.TrackListFragment
import com.zhai.fangying.playlist.fragment.UserPlayListFragment

class ViewPagerAdapter(private val supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager ){

    private val fragments = listOf(TrackListFragment(), UserPlayListFragment())

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Tracks"
            1 -> "Playlist"
            else -> "None"
        }
    }
}