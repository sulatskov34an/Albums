package ru.sulatskov.main.screen.album

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo
import java.lang.Exception

class AlbumRepository: AlbumContractInterface.Repository, KoinComponent{
    private val mainApiService: MainApiService by inject()

    override suspend fun getPhotosRemote(albumId: Int?): MutableList<Photo> {
        var photos = mutableListOf<Photo>()
        CoroutineScope(Dispatchers.Default).async {
            try {
                photos = mainApiService.getPhotosByAlbumId(albumId = albumId).await()
            } catch (e: Exception) {
                Log.d("Exception ${javaClass.simpleName}", e.toString())
            }
        }.await()
        return photos
    }
}