package com.zhai.fangying.playlist.utils

import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL

class Utility {
    companion object {
        fun downloadUrl(url: String) : String?{
            try
            {
                val URL = URL(url);
                val bufferedReader = BufferedReader( InputStreamReader(URL.openStream()));
                var str : String? = "";
                val sb = StringBuilder();
                while (true)
                {
                    str = bufferedReader.readLine()
                    if(str == null) {
                        break;
                    }
                    sb.append(str);
                }
                bufferedReader.close()
                return sb.toString()
            }
            catch (e : Exception) {
                e.printStackTrace()
            }

            return null;

        }
    }
}
