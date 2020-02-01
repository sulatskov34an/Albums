package ru.sulatskov.model.prefs

import android.content.Context
import androidx.core.content.edit

class PrefsService(context: Context) {
    private val prefs = context.getSharedPreferences("carDB", Context.MODE_PRIVATE)

    companion object {
        const val someKey = "someKey"
    }

    var someValue: Boolean
        get() = prefs.getBoolean(someKey, false)
        set(value) {
            prefs.edit {
                putBoolean(someKey, value)
            }
        }
}