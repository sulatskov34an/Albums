package ru.sulatskov.common.providers

import android.content.Context
import ru.sulatskov.R

class StringProvider(val context: Context) {

    fun getToolbarNameAlbum() = context.getString(R.string.album_text)
    fun getToolbarNameMain() = context.getString(R.string.main_text)
    fun getToolbarNameFilter() = context.getString(R.string.filters)
    fun getToolbarNamePhotos() = context.getString(R.string.photos)
}
