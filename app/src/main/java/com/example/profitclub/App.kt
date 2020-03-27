package com.example.profitclub

import RequestChatSocket
import android.app.Application
import android.app.NotificationManager
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationChannel
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import java.lang.Exception
import kotlin.concurrent.thread

class App : Application() {
    private lateinit var socket: Socket

    override fun onCreate() {
        super.onCreate()
        LocaleManager.setLocale(this)
        //MultiDex.install(this)
        createNotificationChannels()
        try {
            socket = IO.socket("http://87.237.236.184")

            socket.on(Socket.EVENT_CONNECT) {
                thread {
                }
            }

            socket.on("new-connection") {
                thread {
                    socket.emit("connect-user", Gson().toJson(RequestChatSocket()))
                }
            }
            socket.connect()
        }catch (e: Exception) {
            toast(e.message.toString())
        }
    }

    val getSocket: Socket
        get() = socket

    //Setup Locale manager with context
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    @Override
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is Channel 1"

            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                "Channel 2",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "This is Channel 2"

            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(channel1)
            manager!!.createNotificationChannel(channel2)
        }
    }

    companion object {
        const val CHANNEL_1_ID = "channel1"
        const val CHANNEL_2_ID = "channel2"
    }
}