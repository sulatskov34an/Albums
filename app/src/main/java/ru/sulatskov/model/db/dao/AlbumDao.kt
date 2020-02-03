package ru.sulatskov.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.sulatskov.model.db.entity.AlbumEntity

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album;")
    fun getAll(): MutableList<AlbumEntity>

    @Query("SELECT * FROM album WHERE id = :id")
    fun getAlbumById(id: Long?): MutableList<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(albumEntity: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(albums: List<AlbumEntity>)

    @Query("DELETE FROM album WHERE album.id= :albumId")
    fun deleteAlbumById(albumId: Long?)

    @Query("DELETE FROM album")
    fun deleteAll()

}