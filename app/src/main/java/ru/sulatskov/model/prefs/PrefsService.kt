package ru.sulatskov.model.prefs

import android.content.Context
import androidx.core.content.edit

class PrefsService(context: Context) {
    private val prefs = context.getSharedPreferences("album", Context.MODE_PRIVATE)

    companion object {
        const val HAS_DB_KEY = "hasDBKey"
    }

    var hasDB: Boolean
        get() = prefs.getBoolean(HAS_DB_KEY, false)
        set(value) {
            prefs.edit {
                putBoolean(HAS_DB_KEY, value)
            }
        }
}