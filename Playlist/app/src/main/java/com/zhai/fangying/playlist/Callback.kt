package com.zhai.fangying.playlist

public interface Callback<T> {
    fun onPostResult(data: T)
    fun onError()
}