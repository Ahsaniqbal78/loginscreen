package com.example.loginapp.ui.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.loginapp.R

class PlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var notificationManager: NotificationManager? = null
    private val channelId = "MediaPlayer"
    private val channelName = "MyMediaPlayer"
    private val notificationId = 123

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationChannel()
        mediaPlayer = MediaPlayer.create(this,R.raw.mm)
        mediaPlayer?.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.d("checkkk", "onStartCommand: $action")
        when (action) {
            ACTION_PLAY -> {
                startMusic()
            }

            ACTION_PAUSE -> {
                pauseMusic()
            }

            ACTION_STOP -> {
                stopMusic()
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }


    private fun startMusic() {
        if (!mediaPlayer?.isPlaying!!) {
            mediaPlayer?.start()
        }
    }

    private fun pauseMusic() {
        if (mediaPlayer?.isPlaying == true){
            mediaPlayer?.pause()
        }
    }

    private fun stopMusic() {
        if (mediaPlayer?.isPlaying == true){
            mediaPlayer?.stop()
        }
    }


        private fun notificationChannel() {
            val playIntent = Intent(this, PlayerService::class.java).apply {
                action = ACTION_PLAY
            }

            val pauseIntent = Intent(this, PlayerService::class.java).apply {
                action = ACTION_PAUSE
            }

            val playPendingIntent = PendingIntent.getService(
                this,
                0,
                playIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val pausePendingIntent = PendingIntent.getService(
                this,
                0,
                pauseIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                notificationManager?.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(this, channelId)
                .setContentIntent(buildPendingIntent())
                .setContentTitle("Music Player")
                .setContentText("Playing Music")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .addAction(R.drawable.play_ic, "play", playPendingIntent)
                .addAction(R.drawable.pause_button, "pause", pausePendingIntent)
                .build()

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationId, notification)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                startForeground(
                    notificationId,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                )
            } else {
                startForeground(notificationId, notification)
            }
        }

private fun buildPendingIntent(): PendingIntent? {
    val intent = Intent(applicationContext, PlayerService::class.java)
    intent.action = Intent.ACTION_MAIN
    intent.addCategory(Intent.CATEGORY_LAUNCHER)

    return PendingIntent.getActivity(
        applicationContext,
        4502,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
}

    companion object {
        const val ACTION_PLAY = "play"
        const val ACTION_PAUSE = "pause"
        const val ACTION_STOP = "stop"
    }

override fun onDestroy() {
    super.onDestroy()
    mediaPlayer?.release()
    mediaPlayer = null
}
}