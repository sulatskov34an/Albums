package ru.sulatskov.model.prefs

import android.content.Context
import androidx.core.content.edit

class PrefsService(context: Context) {
    private val prefs = context.getSharedPreferences("carDB", Context.MODE_PRIVATE)

    companion object {
        const val hasDBKey = "hasDBKey"
    }

    var hasDB: Boolean
        get() = prefs.getBoolean(hasDBKey, false)
        set(value) {
            prefs.edit {
                putBoolean(hasDBKey, value)
            }
        }
}