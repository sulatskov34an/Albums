package ru.sulatskov.common

import android.content.Context
import ru.sulatskov.R

class StringProvider(val context: Context) {

    fun getToolbarNameAlbum() = context.getString(R.string.album_text)
    fun getToolbarNameMain() = context.getString(R.string.main_text)
}
