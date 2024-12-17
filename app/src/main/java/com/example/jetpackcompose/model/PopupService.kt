package com.example.jetpackcompose.service

import android.app.*
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat

class PopupService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 5000L // 5 seconds for demonstration

    // Runnable that will show a popup periodically
    private val showPopupRunnable = object : Runnable {
        override fun run() {
            Toast.makeText(applicationContext, "Hello World", Toast.LENGTH_SHORT).show()
            handler.postDelayed(this, delayMillis)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        // Start the service in the foreground with a notification
        startForeground(1, getNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start showing popups
        handler.post(showPopupRunnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop showing popups
        handler.removeCallbacks(showPopupRunnable)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "popup_service_channel",
            "Popup Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this, "popup_service_channel")
            .setContentTitle("Popup Service Running")
            .setContentText("This service will show Hello World popups every 5 seconds.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}
