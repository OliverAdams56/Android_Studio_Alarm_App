package com.example.wakeup.data

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.wakeup.MainActivity
import com.example.wakeup.R.drawable.ic_stat_name
import com.example.wakeup.R.raw.paino

class AlarmReceiver : BroadcastReceiver()
{

    override fun onReceive(context: Context, intent: Intent)
    { // Define IDs for the two notification channels
        val alarmChannelId = "alarm_channel"
        val reminderChannelId = "reminder_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(context, "Notification permission is not granted.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Handle notification channel creation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            createNotificationChannel(context, alarmChannelId, true)
            createNotificationChannel(context, alarmChannelId, false)
        }

        val startAlarmIntent = Intent(context, AlarmService::class.java).apply {
            action = "START_ALARM"
        }
        context.startService(startAlarmIntent)

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("ALARM_ID", 1)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val stopAlarmIntent = Intent(context, AlarmService::class.java).apply {
            action = "STOP_ALARM"
        }
        val stopAlarmPendingIntent = PendingIntent.getService(context, 1, stopAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Create and display the notification
        val notification = NotificationCompat.Builder(context, alarmChannelId).setContentTitle("Alarm").setContentText("Your alarm time has been reached!").setSmallIcon(ic_stat_name).setPriority(NotificationCompat.PRIORITY_HIGH).setSound(Uri.parse("android.resource://${context.packageName}/$paino")).setContentIntent(openAppPendingIntent).addAction(ic_stat_name, "Stop Alarm", stopAlarmPendingIntent).setAutoCancel(true).build()
        NotificationManagerCompat.from(context).notify(1, notification)

        val reminderNotification = NotificationCompat.Builder(context, reminderChannelId).setContentTitle("Reminder").setContentText("Get ready for your alarm!").setSmallIcon(ic_stat_name).setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(openAppPendingIntent).addAction(ic_stat_name, "Stop Alarm", stopAlarmPendingIntent).setAutoCancel(true).build()
        NotificationManagerCompat.from(context).notify(2, reminderNotification)

        SliderState.setSliderEnabled(true)
    }

    private fun createNotificationChannel(context: Context, channelId: String, withSound: Boolean)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // Check if channel already exists
            val existingChannel = notificationManager.getNotificationChannel(channelId)
            if (existingChannel != null)
            {
                Log.d("AlarmReceiver", "Channel $channelId already exists.")
                return
            }
            Log.d("AlarmReceiver", "Creating channel $channelId.")

            val channelName = if (channelId == "alarm_channel") "Alarm Channel" else "Reminder Channel"
            val channelDescription = if (channelId == "alarm_channel") "Channel for alarm notifications" else "Channel for reminder notifications"
            val importance = if (channelId == "alarm_channel") NotificationManager.IMPORTANCE_HIGH else NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
                enableVibration(true)
                if (withSound)
                {
                    val soundUri = Uri.parse("android.resource://${context.packageName}/$paino")
                    setSound(soundUri, AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build())
                }

            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}
