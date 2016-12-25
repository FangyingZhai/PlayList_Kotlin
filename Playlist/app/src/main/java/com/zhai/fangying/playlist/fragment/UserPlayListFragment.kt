package com.zhai.fangying.playlist.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhai.fangying.playlist.Callback
import com.zhai.fangying.playlist.R
import com.zhai.fangying.playlist.activity.TrackDetailActivity
import com.zhai.fangying.playlist.adapter.TrackListAdapter
import com.zhai.fangying.playlist.db.PlayListDatabaseHelper
import com.zhai.fangying.playlist.db.TrackDbDeleteTrackAsyncTask
import com.zhai.fangying.playlist.db.TrackDbReadAsyncTask
import com.zhai.fangying.playlist.db.TrackDbWriteAsyncTask
import com.zhai.fangying.playlist.model.Track
import kotlinx.android.synthetic.main.fragment_play_list.*
import kotlinx.android.synthetic.main.fragment_track_list.*

class UserPlayListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_play_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        val view = view?:return
        TrackDbReadAsyncTask(PlayListDatabaseHelper(view.context), object : Callback<List<Track>> {
            override fun onPostResult(data: List<Track>) {
                activity?.runOnUiThread {
                    if(data.isEmpty()) {
                        list_view.visibility = View.GONE
                        emptyPlaylist.visibility = View.VISIBLE
                        Toast.makeText(view.context, "Playlist empty", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        list_view.visibility = View.VISIBLE
                        emptyPlaylist.visibility = View.GONE
                        list_view.adapter = TrackListAdapter(data)

                        list_view.setOnItemLongClickListener { parent, view, position, id ->
                            val item = data[position]
                            val alertDialog = AlertDialog.Builder(view.context);
                            alertDialog.setTitle("Confirm")
                            alertDialog.setMessage("Are you sure want to delete ${item.name} from playlist")

                            alertDialog.setPositiveButton("OK"
                            ) { dialog, _ ->
                                TrackDbDeleteTrackAsyncTask(PlayListDatabaseHelper(view.context),
                                    object : Callback<Boolean> {
                                        override fun onPostResult(data: Boolean) {
                                            activity?.runOnUiThread {
                                                fetchData()
                                            }
                                        }

                                        override fun onError() {
                                            activity?.runOnUiThread {
                                                Toast.makeText(view.context, "Error while deleting", Toast.LENGTH_SHORT).show()
                                            }                                        }

                                    }).execute(item)
                            }

                            alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }

                            alertDialog.show()
                            return@setOnItemLongClickListener true
                        }

                        list_view.setOnItemClickListener { parent, view, position, id ->
                            val track = list_view?.adapter?.getItem(position) as Track?
                            track?:return@setOnItemClickListener

                            startActivity(Intent(view.context, TrackDetailActivity::class.java).apply {
                                putExtra("DATA", track)
                                putExtra("IN_PLAY_LIST", false)
                            })
                        }
                    }
                }
            }

            override fun onError() {
                activity?.runOnUiThread {
                    Toast.makeText(view.context, "Error while fetching", Toast.LENGTH_SHORT).show()
                }
            }

        }).execute()
    }

}