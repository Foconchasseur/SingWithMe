package com.example.singwithme

import android.app.Application

class KaraokeApplication : Application() {
    companion object {
        lateinit var instance: KaraokeApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}