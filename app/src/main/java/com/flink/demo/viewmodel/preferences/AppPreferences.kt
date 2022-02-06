package com.flink.demo.viewmodel.preferences

import android.content.Context
import android.content.SharedPreferences
import com.flink.demo.BuildConfig
import com.flink.demo.viewmodel.extentions.get
import com.flink.demo.viewmodel.extentions.put
import com.google.gson.Gson

class AppPreferences {

    companion object{

        private lateinit var preferences: SharedPreferences
        private lateinit var jsonConverter: Gson

        private const val AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY"
        private const val SESSION_ID_KEY = "SESSION_ID_KEY"

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

    }
}