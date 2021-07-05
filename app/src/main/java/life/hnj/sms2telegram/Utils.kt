package life.hnj.sms2telegram

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun sync2TelegramKey(resources: android.content.res.Resources): Preferences.Key<Boolean> {
    return booleanPreferencesKey(resources.getString(R.string.enable_telegram_sync_key))
}

fun getBooleanVal(
    applicationContext: android.content.Context,
    key: Preferences.Key<Boolean>
): Boolean {
    val sync2TgEnabledFlow =
        applicationContext.dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    return runBlocking { sync2TgEnabledFlow.first() }
}