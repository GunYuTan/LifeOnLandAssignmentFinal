package com.example.lifeonlandassignment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment

class NotificationFragment : Fragment() {

    private val CHANNEL_ID = "your_channel_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create Notification Channel for Android Oreo and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Your Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Get layouts for the custom notification views
        val notificationLayout = RemoteViews(context?.packageName, R.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(context?.packageName, R.layout.notification_large)

        // Build the custom notification
        val customNotification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .build()

        // Trigger the notification
        notificationManager.notify(1, customNotification)
    }
}