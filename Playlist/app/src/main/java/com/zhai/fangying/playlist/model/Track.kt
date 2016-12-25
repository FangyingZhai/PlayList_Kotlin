package com.zhai.fangying.playlist.model

import java.io.Serializable

data class Track (
    val name : String,
    val artist: String?,
    val image: String?,
    val published: String? = null,
    val tags : String? = null,
    val playCount: String? = null
) : Serializable