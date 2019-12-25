package com.younghong.learngles

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

}