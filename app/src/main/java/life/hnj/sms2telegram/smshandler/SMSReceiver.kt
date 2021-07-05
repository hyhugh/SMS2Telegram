package life.hnj.sms2telegram.smshandler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import life.hnj.sms2telegram.getBooleanVal
import life.hnj.sms2telegram.sync2TelegramKey


class SMSReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        val sync2TgEnabledKey = sync2TelegramKey(context.resources)
        val sync2TgEnabled = getBooleanVal(context, sync2TgEnabledKey)
        if (!sync2TgEnabled) {
            Log.d("SMSHandler", "sync2TgEnabled is false, returning")
            return
        }

        Log.d("SMSHandler", "sync2TgEnabled, and received new sms")
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val bundle = intent.extras
        val format = bundle?.getString("format")
        val pdus = bundle!!["pdus"] as Array<*>?
        Log.d("SMSHandler", bundle.toString())

        if (pdus != null) {
            val msgs: List<SmsMessage?> =
                pdus.map { i -> SmsMessage.createFromPdu(i as ByteArray, format) }
            for (msg in msgs) {
                // Build the message to show.
                val strMessage = """
                    New SMS from ${msg?.originatingAddress}
                    to sim slot ${bundle.getInt("android.telephony.extra.SLOT_INDEX", -1)}
                    ${msg?.messageBody}
                """.trimIndent()

                // Log and display the SMS message.
                Log.d("SMSHandler", "onReceive: $strMessage")
                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}