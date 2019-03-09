package com.zhai.fangying.playlist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlayListDatabaseHelper(context: Context): SQLiteOpenHelper(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createPlayListTableQuery = "CREATE TABLE " + DbSettings.DBPlayListEntry.TABLE + " ( " +
                DbSettings.DBPlayListEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSettings.DBPlayListEntry.COL_TRACK_NAME + " TEXT NULL, " +
                DbSettings.DBPlayListEntry.COL_ARTIST + " TEXT NULL, " +
                DbSettings.DBPlayListEntry.COL_TRACK_URL + " TEXT NULL, " +
                DbSettings.DBPlayListEntry.COL_TRACK_PLAY_COUNT + " TEXT NULL, " +
                DbSettings.DBPlayListEntry.COL_TAGS + " TEXT NULL, " +
                DbSettings.DBPlayListEntry.COL_PUBLISHED + " TEXT NULL);"

        db?.execSQL(createPlayListTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DbSettings.DBPlayListEntry.TABLE)
        onCreate(db)
    }
}