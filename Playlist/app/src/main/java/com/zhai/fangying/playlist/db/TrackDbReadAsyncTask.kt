package com.zhai.fangying.playlist.db

import android.os.AsyncTask
import com.zhai.fangying.playlist.Callback
import com.zhai.fangying.playlist.model.Track

class TrackDbReadAsyncTask(private val dbHelper: PlayListDatabaseHelper, private val callback : Callback<List<Track>>?): AsyncTask<Void?, Void?, Void?>() {
    override fun doInBackground(vararg params: Void?): Void? {

        val database = dbHelper.readableDatabase

        val cursor = database.query(
            DbSettings.DBPlayListEntry.TABLE,
            null,
            null, null, null, null, null)

        val trackList = ArrayList<Track>()

        while (cursor.moveToNext()) {
            val cursorId = cursor.getColumnIndex(DbSettings.DBPlayListEntry.ID)
            val cursorTrackName = cursor.getColumnIndex(DbSettings.DBPlayListEntry.COL_TRACK_NAME)
            val cursorTrackUrl = cursor.getColumnIndex(DbSettings.DBPlayListEntry.COL_TRACK_URL)
            val cursorPlayCount = cursor.getColumnIndex(DbSettings.DBPlayListEntry.COL_TRACK_PLAY_COUNT)
            val cursorTags = cursor.getColumnIndex(DbSettings.DBPlayListEntry.COL_TAGS)
            val cursorArtist = cursor.getColumnIndex(DbSettings.DBPlayListEntry.COL_ARTIST)
            val cursorPublished = cursor.getColumnIndex(DbSettings.DBPlayListEntry.COL_PUBLISHED)

            trackList.add(
                Track(
                    name = cursor.getString(cursorTrackName),
                    image = cursor.getString(cursorTrackUrl),
                    playCount = cursor.getString(cursorPlayCount),
                    tags = cursor.getString(cursorTags),
                    published = cursor.getString(cursorPublished),
                    artist = cursor.getString(cursorArtist)
            ))
        }

        cursor.close()

        callback?.onPostResult(trackList)

        return null;
    }

}