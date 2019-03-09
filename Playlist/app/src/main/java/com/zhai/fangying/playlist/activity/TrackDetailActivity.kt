package com.zhai.fangying.playlist.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.zhai.fangying.playlist.Callback
import com.zhai.fangying.playlist.R
import com.zhai.fangying.playlist.db.PlayListDatabaseHelper
import com.zhai.fangying.playlist.db.TrackDbWriteAsyncTask
import com.zhai.fangying.playlist.model.Track
import com.zhai.fangying.playlist.network.ApiClient
import kotlinx.android.synthetic.main.activity_track_details.*
import org.json.JSONObject

class TrackDetailActivity: AppCompatActivity() {

    private val canShowAddToPlayListButton : Boolean by lazy {
        intent?.getBooleanExtra("IN_PLAY_LIST", true) ?: true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_track_details)

        addToPlayList.visibility = View.GONE

        val extra = intent?.getSerializableExtra("DATA")
        if(extra == null) {
            finish()
            return
        }

        val track = extra as Track

        fetchDetails(track);
    }


    private fun fetchDetails(track: Track) {
        val url = MainActivity.BASE_URL +"&method=track.getInfo&artist="+track.artist+"&track="+track.name;

        ApiClient(object : Callback<String> {
            override fun onPostResult(data: String) {
                val jsonTrack = JSONObject(data).getJSONObject("track");

                val publishedOn = jsonTrack.optJSONObject("wiki")?.optString("published")

                val trackDetail = Track(track.name, track.artist, track.image,
                    published = publishedOn,
                    tags = jsonTrack.getJSONObject("toptags").getJSONArray("tag")
                        .let {
                            val tag = ArrayList<String>();
                            for(i in 0 until it.length()) {
                                tag.add(it.getJSONObject(i).getString("name"))
                            }
                            return@let tag.joinToString(",")
                        }
                    ,
                    playCount =  jsonTrack.getString("playcount")
                    )


                runOnUiThread {
                    if(canShowAddToPlayListButton)
                        addToPlayList.visibility = View.VISIBLE
                    Glide.with(this@TrackDetailActivity).load(trackDetail.image).into(cover_image_view);
                    track_name.text = trackDetail.name
                    artist_name.text = trackDetail.artist
                    tags.text = trackDetail.tags
                    published.text = "Published: "+ trackDetail.published
                    playCount.text = "Play Count: "+trackDetail.playCount

                    addToPlayList.setOnClickListener {
                        TrackDbWriteAsyncTask(PlayListDatabaseHelper(this@TrackDetailActivity), object :Callback<Any> {
                            override fun onPostResult(data: Any) {
                                runOnUiThread {
                                    Toast.makeText(this@TrackDetailActivity, "Added to playlist", Toast.LENGTH_SHORT).show()
                                }                            }

                            override fun onError() {
                                runOnUiThread {
                                    Toast.makeText(this@TrackDetailActivity, "Error while adding to playlist", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }).execute(trackDetail)
                    }
                }
            }

            override fun onError() {
                runOnUiThread {
                    Toast.makeText(this@TrackDetailActivity, "Error while fetching", Toast.LENGTH_SHORT).show()
                }
            }

        }).execute(url)
    }
}