package ru.sulatskov.model.prefs

import android.content.Context
import androidx.core.content.edit

class PrefsService(context: Context) {
    private val prefs = context.getSharedPreferences("carDB", Context.MODE_PRIVATE)

    companion object {
        const val hasDBKey = "hasDBKey"
        const val hasConnectionKey = "hasConnectionKey"
    }

    var hasDB: Boolean
        get() = prefs.getBoolean(hasDBKey, false)
        set(value) {
            prefs.edit {
                putBoolean(hasDBKey, value)
            }
        }
    var hasConnection: Boolean
        get() = prefs.getBoolean(hasConnectionKey, false)
        set(value) {
            prefs.edit {
                putBoolean(hasConnectionKey, value)
            }
        }
}