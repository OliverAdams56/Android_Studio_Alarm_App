package com.example.wakeup.data

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.example.wakeup.MainActivity
import com.example.wakeup.R.raw.paino

class AlarmService : Service()
{

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        when (intent.action)
        {
            "START_ALARM" -> startAlarm()
            "STOP_ALARM" -> stopAlarm()
        }
        return START_STICKY
    }

    private fun startAlarm()
    {
        if (mediaPlayer == null)
        {
            mediaPlayer = MediaPlayer.create(this, paino).apply {
                setOnCompletionListener { stopAlarm() }
                start()
            }
            Log.d("AlarmService", "Alarm started.")
        }
    }

    private fun stopAlarm()
    {
        mediaPlayer?.let {
            if (it.isPlaying)
            {
                it.stop()
            }
            it.release()
            mediaPlayer = null
            Log.d("AlarmService", "Alarm stopped.")
            // Launch MainActivity when stopping the alarm
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }


    override fun onBind(intent: Intent): IBinder?
    {
        return null
    }

    override fun onDestroy()
    {
        stopAlarm()
        super.onDestroy()
    }
}