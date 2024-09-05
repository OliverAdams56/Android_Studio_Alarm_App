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
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.wakeup.MainActivity
import com.example.wakeup.R.drawable.ic_stat_name
import com.example.wakeup.R.raw.paino

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmChannelId = "alarm_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    context, "Notification permission is not granted.", Toast.LENGTH_LONG
                ).show()
                return
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            createNotificationChannel(context, alarmChannelId)
        }

        val startAlarmIntent = Intent(context, AlarmService::class.java).apply {
            action = "START_ALARM"
        }
        context.startService(startAlarmIntent)

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("ALARM_ID", 1)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val openAppPendingIntent = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopAlarmIntent = Intent(context, AlarmService::class.java).apply {
            action = "STOP_ALARM"
        }
        val stopAlarmPendingIntent = PendingIntent.getService(
            context,
            1,
            stopAlarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(context, alarmChannelId).setContentTitle("Alarm")
                .setContentText("Your alarm time has been reached!").setSmallIcon(ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Uri.parse("android.resource://${context.packageName}/$paino"))
                .setContentIntent(openAppPendingIntent)
                .addAction(ic_stat_name, "Stop Alarm", stopAlarmPendingIntent).setAutoCancel(true)
                .build()
        NotificationManagerCompat.from(context).notify(1, notification)

        SliderState.setSliderEnabled(true)
    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channelName = "Alarm Channel"
            val channelDescription = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
                enableVibration(true)
                val soundUri = Uri.parse("android.resource://${context.packageName}/$paino")
                setSound(
                    soundUri,
                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
                )
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}