package ru.sulatskov.main.screen.general

import android.util.Log
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.model.db.AlbumsDataBaseService
import ru.sulatskov.model.db.entity.AlbumEntity
import ru.sulatskov.model.network.Album
import ru.sulatskov.model.network.MainApiService
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class GeneralRepository : GeneralContractInterface.Repository, KoinComponent, CoroutineScope {

    private val mainApiService: MainApiService by inject()
    private val dbService: AlbumsDataBaseService by inject()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    override suspend fun getAlbumsRemote(): MutableList<Album> = withContext(coroutineContext) {
        try {
            mainApiService.getAlbums().await()
        } catch (e: Exception) {
            Log.d("Exception ${javaClass.simpleName}", e.toString())
            mutableListOf<Album>()
        }
    }

    override suspend fun getAlbumsDB(): MutableList<Album> = withContext(coroutineContext) {
        try {
            dbService.getAllAlbums().map { album ->
                Album(
                    id = album.id,
                    title = album.title,
                    userId = album.userId
                )
            }.toMutableList()
        } catch (e: Exception) {
            Log.d("Exception ${javaClass.simpleName}", e.toString())
            mutableListOf<Album>()
        }
    }

    override fun insertAlbums(albums: MutableList<Album>) {
        launch {
            try {
                dbService.insertAlbumsList(albums = albums.map { album ->
                    AlbumEntity(
                        id = album.id,
                        userId = album.userId,
                        title = album.title
                    )
                })
            } catch (e: Exception) {
                Log.d("Exception ${javaClass.simpleName}", e.toString())
            }
        }
    }
}