package life.hnj.sms2telegram

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        setSupportActionBar(findViewById(R.id.action_bar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val horizontalMargin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2F, resources.displayMetrics
            )
            val verticalMargin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2F, resources.displayMetrics
            )
            val topMargin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(R.dimen.activity_vertical_margin) + 10,
                resources.displayMetrics
            )
            listView.setPadding(
                horizontalMargin.toInt(),
                topMargin.toInt(), horizontalMargin.toInt(), verticalMargin.toInt()
            )

            super.onViewCreated(view, savedInstanceState)

        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}