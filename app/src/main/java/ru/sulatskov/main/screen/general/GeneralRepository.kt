package ru.sulatskov.main.screen.general

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.model.db.AlbumsDataBaseService
import ru.sulatskov.model.db.entity.AlbumEntity
import ru.sulatskov.model.network.Album
import ru.sulatskov.model.network.MainApiService
import java.lang.Exception

class GeneralRepository: GeneralContractInterface.Repository, KoinComponent{

    private val mainApiService: MainApiService by inject()
    private val dbService: AlbumsDataBaseService by inject()


    override suspend fun getAlbumsRemote(): MutableList<Album> {
        var albums = mutableListOf<Album>()
        CoroutineScope(Dispatchers.Default).async {
            try {
                albums = mainApiService.getAlbums().await()
            } catch (e: Exception) {
                Log.d("Exception ${javaClass.simpleName}", e.toString())
            }
        }.await()
        return albums
    }

    override suspend fun getAlbumsDB(): MutableList<Album> {
        var albums = mutableListOf<Album>()
        CoroutineScope(Dispatchers.Default).async {
            try {
                albums = dbService.getAllAlbums().map { album ->
                    Album(
                        id = album.id,
                        title = album.title,
                        userId = album.userId
                    )
                }.toMutableList()
            } catch (e: Exception) {
                Log.d("Exception ${javaClass.simpleName}", e.toString())
            }
        }.await()
        return albums
    }

    override fun insertAlbums(albums: MutableList<Album>) {
        CoroutineScope(Dispatchers.Default).launch {
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