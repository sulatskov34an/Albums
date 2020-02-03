package ru.sulatskov.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sulatskov.model.db.dao.AlbumDao
import ru.sulatskov.model.db.entity.AlbumEntity

@Database(
    entities = [(AlbumEntity::class)], version = 1, exportSchema = false
)
abstract class AlbumsDataBase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        var sInstance: AlbumsDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AlbumsDataBase {
            if (sInstance == null) {
                sInstance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AlbumsDataBase::class.java,
                        "albumsDB"
                    ).build()
            }
            return sInstance!!
        }
    }
}