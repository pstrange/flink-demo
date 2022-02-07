package com.flink.demo.viewmodel.preferences

import android.content.Context
import android.content.SharedPreferences
import com.flink.demo.BuildConfig
import com.flink.demo.viewmodel.extentions.get
import com.flink.demo.viewmodel.extentions.put
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppPreferences {

    companion object{

        private lateinit var preferences: SharedPreferences
        private lateinit var jsonConverter: Gson

        private const val AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY"
        private const val SESSION_ID_KEY = "SESSION_ID_KEY"
        private const val BOOKMARKS_KEY = "BOOKMARKS_KEY"

        const val IMAGE_PATH_SMALL = BuildConfig.IMAGE_HOST+"w300/"
        const val IMAGE_PATH_BIG = BuildConfig.IMAGE_HOST+"w500/"

        fun init(ctx: Context) {
            jsonConverter = Gson()
            preferences = ctx.getSharedPreferences(
                BuildConfig.CONFIGS_NAME, Context.MODE_PRIVATE)
        }

        var AUTH_TOKEN: String
            get() = preferences.get(AUTH_TOKEN_KEY, "")
            set(value) = preferences.put(AUTH_TOKEN_KEY, value)

        var SESSION_ID: String
            get() = preferences.get(SESSION_ID_KEY, "")
            set(value) = preferences.put(SESSION_ID_KEY, value)

        var BOOKMAKS: List<Long>
            get(){
                val json = preferences.get(BOOKMARKS_KEY, "")
                if(json.isNullOrEmpty() || json == "null") return ArrayList()
                val typeArray = object : TypeToken<ArrayList<Long>>(){}.type
                return jsonConverter.fromJson(json, typeArray)
            }
            set(value) = preferences.put(BOOKMARKS_KEY, jsonConverter.toJson(value))
    }
}