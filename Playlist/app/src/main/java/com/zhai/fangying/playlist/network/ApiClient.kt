package com.zhai.fangying.playlist.network

import android.os.AsyncTask
import com.zhai.fangying.playlist.Callback
import com.zhai.fangying.playlist.utils.Utility

class ApiClient(private val callback : Callback<String>?): AsyncTask<String, Void?, Void?>() {

    override fun doInBackground(vararg params: String?): Void? {
        val url = params[0]
        url ?: return null
        Utility.downloadUrl(url)?.let {
            callback?.onPostResult(it)
        } ?: callback?.onError()

        return null;
    }

}