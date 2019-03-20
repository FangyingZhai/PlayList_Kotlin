package com.zhai.fangying.playlist.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Trace
import com.zhai.fangying.playlist.Callback
import com.zhai.fangying.playlist.model.Track
import com.zhai.fangying.playlist.utils.Utility

class TrackDbDeleteTrackAsyncTask(private val dbHelper: PlayListDatabaseHelper, private val callback : Callback<Boolean>?): AsyncTask<Track, Void?, Void?>() {
    override fun doInBackground(vararg params: Track?): Void? {
        val track = params[0];
        track?:return null
        val db = dbHelper.writableDatabase

        db.execSQL("delete from "+
                DbSettings.DBPlayListEntry.TABLE+
                " where "+DbSettings.DBPlayListEntry.COL_TRACK_NAME+
                " = \""+track.name+"\"")

        callback?.onPostResult(true)

        return null;
    }

}