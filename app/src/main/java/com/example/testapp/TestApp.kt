package com.example.testapp

import android.app.Application

class TestApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: TestApp? = null

        fun applicationContext(): TestApp {
            return instance as TestApp
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}