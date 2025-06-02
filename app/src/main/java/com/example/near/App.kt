package com.example.near

import android.app.Application
import com.example.near.service.FcmTokenManager
import com.example.near.service.FcmTokenManagerHolder
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var fcmTokenManager: FcmTokenManager

    override fun onCreate() {
        super.onCreate()
        FcmTokenManagerHolder.init(fcmTokenManager)
    }
}