package com.example.wakeup.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wakeup.R.drawable.ic_stat_name
import com.example.wakeup.R.raw.paino

class AlarmReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        val notificationId = 1
        val channelId = "alarm_channel"

        playAlarmSound(context)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            val soundUri = Uri.parse("android.resource://${context.packageName}/$paino")

            val channel = NotificationChannel(channelId, "Alarm Channel", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Channel for alarm notifications"
                setSound(soundUri, AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build())
                enableVibration(true)

            }
            notificationManager.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            if (notificationManagerCompat.areNotificationsEnabled())
            {
                val notification = NotificationCompat.Builder(context, channelId).setContentTitle("Alarm").setContentText("Your alarm time has been reached!").setSmallIcon(ic_stat_name).setPriority(NotificationCompat.PRIORITY_HIGH).setSound(Uri.parse("android.resource://${context.packageName}/$paino")).setAutoCancel(true).build()

                notificationManagerCompat.notify(notificationId, notification)

                SliderState.setSliderEnabled(true)
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

            SliderState.setSliderEnabled(true)
        }
    }
}

private fun playAlarmSound(context: Context)
{
    val mediaPlayer = MediaPlayer.create(context, paino)
    mediaPlayer.setOnCompletionListener { mp -> mp.release() }
    mediaPlayer.start()
}