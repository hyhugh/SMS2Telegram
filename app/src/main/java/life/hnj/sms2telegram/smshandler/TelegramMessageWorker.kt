package life.hnj.sms2telegram.smshandler

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TelegramMessageWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // TODO(hugh): implement this
        return Result.success()
    }
}