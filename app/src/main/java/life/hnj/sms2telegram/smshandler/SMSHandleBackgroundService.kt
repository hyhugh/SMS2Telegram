package life.hnj.sms2telegram.smshandler

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.provider.Telephony
import android.util.Log
import android.widget.Toast

class SMSHandleBackgroundService : Service() {
    private var receiver = SMSReceiver()
    private val TAG = "SMSReceiverService"
    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Registering the receiver")
        Toast.makeText(applicationContext, "Registering the receiver", Toast.LENGTH_LONG).show()
        registerReceiver(
            receiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION),
            Manifest.permission.BROADCAST_SMS,
            null
        )
        // Restart when closed
        return START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}