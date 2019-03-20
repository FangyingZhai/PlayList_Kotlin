package com.zhai.fangying.playlist.db

import android.provider.BaseColumns

class DbSettings {
    companion object {
        const val DB_NAME = "playlist.db"
        const val DB_VERSION = 1
    }

    class DBPlayListEntry: BaseColumns {
        companion object {
            const val TABLE = "playlist"
            const val ID = BaseColumns._ID
            const val COL_TRACK_NAME = "name"
            const val COL_TRACK_URL = "url"
            const val COL_TRACK_PLAY_COUNT = "play_count"
            const val COL_ARTIST = "artist"
            const val COL_PUBLISHED = "published"
            const val COL_TAGS = "tags"
        }
    }
}