package com.example.jetpackcompose.service

import android.app.*
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat

class PopupService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 5000L // 5 seconds for demonstration
    private var i = 0

    // Runnable that will send a notification periodically
    private val showNotificationRunnable = object : Runnable {
        override fun run() {
            sendNotification("Hello World $i")
            i++ // Pre-increment the variable i
            handler.postDelayed(this, delayMillis)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        // Start the service in the foreground with an initial notification
        startForeground(1, getNotification("Popup Service Running"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start showing notifications
        handler.post(showNotificationRunnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop showing notifications
        handler.removeCallbacks(showNotificationRunnable)
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

    private fun getNotification(contentText: String): Notification {
        return NotificationCompat.Builder(this, "popup_service_channel")
            .setContentTitle("Popup Service")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun sendNotification(message: String) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notification = getNotification(message)
        notificationManager.notify(1, notification)
    }
}
