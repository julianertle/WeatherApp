package com.example.jetpackcompose.service

import android.app.*
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.PendingIntent
import android.os.Build
import com.example.jetpackcompose.MainActivity

class PopupService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 5000L // 10 seconds for demonstration
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "popup_service_channel",
                "Popup Service Channel",
                NotificationManager.IMPORTANCE_HIGH // High priority for heads-up notifications
            ).apply {
                description = "Notifications from Popup Service"
                enableLights(true)
                enableVibration(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC // Visible on lockscreen
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(contentText: String): Notification {
        // Create an intent to open the app when the notification is tapped
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE // Ensure FLAG_IMMUTABLE is specified
        )

        return NotificationCompat.Builder(this, "popup_service_channel")
            .setContentTitle("Popup Service")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_MAX) // Ensure it's high priority
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE) // Sound and Vibration
            .setContentIntent(pendingIntent) // Open app on tap
            .setAutoCancel(true) // Automatically removes the notification when clicked
            .build()
    }

    private fun sendNotification(message: String) {
        // Check for permission
        if (ActivityCompat.checkSelfPermission(
                this@PopupService,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle missing permission
            return
        }

        // Use the same notification ID to update the ongoing notification
        val notificationManager = NotificationManagerCompat.from(this)
        val notification = getNotification(message) // Create the updated notification
        notificationManager.notify(1, notification) // Use the same ID to update the notification
    }

}
