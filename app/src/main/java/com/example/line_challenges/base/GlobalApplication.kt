package com.example.line_challenges.base

import android.app.Application

class GlobalApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }
    companion object {
        var instance: GlobalApplication? = null
    }
}