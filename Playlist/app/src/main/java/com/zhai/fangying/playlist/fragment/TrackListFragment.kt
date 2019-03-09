package com.zhai.fangying.playlist.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhai.fangying.playlist.Callback
import com.zhai.fangying.playlist.R
import com.zhai.fangying.playlist.activity.MainActivity
import com.zhai.fangying.playlist.activity.TrackDetailActivity
import com.zhai.fangying.playlist.adapter.TrackGridAdapter
import com.zhai.fangying.playlist.model.Track
import com.zhai.fangying.playlist.network.ApiClient
import kotlinx.android.synthetic.main.fragment_track_list.*
import org.json.JSONObject
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class TrackListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_track_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        grid_view.setOnItemClickListener { parent, view, position, id ->
            val track = grid_view?.adapter?.getItem(position) as Track?
            track?:return@setOnItemClickListener

            startActivity(Intent(view.context, TrackDetailActivity::class.java).apply {
                putExtra("DATA", track)
            })
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.isNullOrEmpty()) {
                    getCurrentTracks()
                    return true
                }

                getQueriedTrack(query);
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()) {
                    getCurrentTracks()
                    return true
                }
                return false
            }

        })

        search_view.setOnCloseListener {
            getCurrentTracks()
            return@setOnCloseListener true
        }


    }

    override fun onResume() {
        super.onResume()
        getCurrentTracks();
        search_view.setQuery("", false)
    }

    private fun getCurrentTracks() {
        val url = MainActivity.BASE_URL +"&method=chart.gettoptracks";

        ApiClient(object : Callback<String> {
            override fun onPostResult(data: String) {
                val jsonTracks = JSONObject(data).getJSONObject("tracks").getJSONArray("track")
                val tracks = ArrayList<Track>(jsonTracks.length())

                for(i in 0 until jsonTracks.length()) {
                    val jsonTrack = jsonTracks.getJSONObject(i)
                    tracks.add(
                        Track(jsonTrack.getString("name"),
                            jsonTrack.getJSONObject("artist").getString("name"),
                            jsonTrack.getJSONArray("image").getJSONObject(2).getString("#text")
                        )
                    )
                }

                activity?.runOnUiThread {
                    title.text = "Top Tracks"
                    grid_view.adapter = TrackGridAdapter(tracks)
                }
            }

            override fun onError() {
                activity?.runOnUiThread {
                    Toast.makeText(view?.context, "Error while fetching", Toast.LENGTH_SHORT).show()
                }
            }

        }).execute(url)
    }

    private fun getQueriedTrack(query : String) {
        val url = "${MainActivity.BASE_URL}&method=artist.gettoptracks&artist=$query";

        ApiClient(object : Callback<String> {
            override fun onPostResult(data: String) {
                try {
                    val jsonTracks = JSONObject(data).getJSONObject("toptracks").getJSONArray("track")

                    if (jsonTracks.length() == 0) {
                        activity?.runOnUiThread {
                            Toast.makeText(view?.context, "No result found", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }

                    val tracks = ArrayList<Track>(jsonTracks.length())

                    for (i in 0 until jsonTracks.length()) {
                        val jsonTrack = jsonTracks.getJSONObject(i)
                        tracks.add(
                            Track(
                                jsonTrack.getString("name"),
                                jsonTrack.getJSONObject("artist").getString("name"),
                                jsonTrack.getJSONArray("image").getJSONObject(2).getString("#text")
                            )
                        )
                    }

                    activity?.runOnUiThread {
                        title.text = "Top Tracks for $query"
                        grid_view.adapter = TrackGridAdapter(tracks)
                    }
                } catch (e : Exception) {
                    activity?.runOnUiThread {
                        Toast.makeText(view?.context, "Result not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onError() {
                activity?.runOnUiThread {
                    Toast.makeText(view?.context, "Error while fetching", Toast.LENGTH_SHORT).show()
                }
            }

        }).execute(url)

        context?:return
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view.windowToken, 0)
    //    search_view.clearFocus()
    }
}