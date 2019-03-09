package com.zhai.fangying.playlist.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Trace
import com.zhai.fangying.playlist.Callback
import com.zhai.fangying.playlist.model.Track
import com.zhai.fangying.playlist.utils.Utility

class TrackDbWriteAsyncTask(private val dbHelper: PlayListDatabaseHelper, private val callback : Callback<Any>?): AsyncTask<Track, Void?, Void?>() {
    override fun doInBackground(vararg params: Track?): Void? {

        val track = params[0]
        track?:return null

        val contentValue = ContentValues()
        contentValue.put(DbSettings.DBPlayListEntry.COL_ARTIST, track.artist)
        contentValue.put(DbSettings.DBPlayListEntry.COL_PUBLISHED, track.published)
        contentValue.put(DbSettings.DBPlayListEntry.COL_TAGS, track.tags)
        contentValue.put(DbSettings.DBPlayListEntry.COL_TRACK_NAME, track.name)
        contentValue.put(DbSettings.DBPlayListEntry.COL_TRACK_PLAY_COUNT, track.playCount)
        contentValue.put(DbSettings.DBPlayListEntry.COL_TRACK_URL, track.image)

        val db = dbHelper.writableDatabase

        db.insertWithOnConflict(
            DbSettings.DBPlayListEntry.TABLE,
            null,
            contentValue,
            SQLiteDatabase.CONFLICT_REPLACE
        )

        callback?.onPostResult("Success")

        return null;
    }

}