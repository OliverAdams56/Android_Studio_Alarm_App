package com.example.wakeup.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wakeup.R
import com.example.wakeup.R.drawable.ic_stat_name

class AlarmReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        val notificationId = 1
        val channelId = "alarm_channel"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            val channel = NotificationChannel(channelId, "Alarm Channel", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Channel for alarm notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            if (notificationManagerCompat.areNotificationsEnabled())
            {
                val notification = NotificationCompat.Builder(context, channelId).setContentTitle("Alarm").setContentText("Your alarm time has been reached!").setSmallIcon(ic_stat_name).setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true).build()

                notificationManagerCompat.notify(notificationId, notification)
            }
            else
            {
                Toast.makeText(context, "Please enable notifications for this app.", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            val notification = NotificationCompat.Builder(context, channelId).setContentTitle("Alarm").setContentText("Your alarm time has been reached!").setSmallIcon(ic_stat_name).setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true).build()

            notificationManager.notify(notificationId, notification)
        }
    }
}