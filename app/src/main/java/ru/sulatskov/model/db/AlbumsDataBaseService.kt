package ru.sulatskov.model.db

import android.content.Context
import ru.sulatskov.model.db.entity.AlbumEntity

class AlbumsDataBaseService(context: Context) {
    private var dbService = AlbumsDataBase.getInstance(context)

    fun insertAlbum(albumEntity: AlbumEntity) {
        dbService.albumDao().insert(albumEntity)
    }

    fun insertAlbumsList(albums: List<AlbumEntity>) {
        dbService.albumDao().insertList(albums)
    }

    fun getAllAlbums(): MutableList<AlbumEntity> = dbService.albumDao().getAll()
}