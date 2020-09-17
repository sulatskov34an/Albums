package ru.sulatskov.main.screen.album

import android.util.Log
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class AlbumRepository : AlbumContractInterface.Repository, KoinComponent, CoroutineScope {

    private val mainApiService: MainApiService by inject()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override suspend fun getPhotosRemote(albumId: Int?) = withContext(coroutineContext) {
        try {
            mainApiService.getPhotosByAlbumId(albumId = albumId)
        } catch (e: Exception) {
            Log.d("Exception ${javaClass.simpleName}", e.toString())
            mutableListOf<Photo>()
        }
    }

}