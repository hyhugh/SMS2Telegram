package life.hnj.sms2telegram.smshandler

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import life.hnj.sms2telegram.MainActivity

class SMSHandleForegroundService : Service() {
    private var receiver = SMSReceiver()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Registering the receiver")
        Toast.makeText(applicationContext, "Registering the receiver", Toast.LENGTH_LONG).show()

        val notification = createNotification()
        startForeground(1, notification)

        registerReceiver(
            receiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION),
            Manifest.permission.BROADCAST_SMS,
            null
        )
        // Restart when closed
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        val input = "SMS2Telegram running in the background"
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)
        val channelId = createNotificationChannel("SMS2TELEGRAM", "SMS2TelegramService")
        return NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("SMS2Telegram Service")
            .setContentText(input)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }

    private companion object {
        const val TAG = "SMSReceiverService"
    }
}
