package ru.sulatskov.main.screen.slider

import android.util.Log
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class SliderRepository : SliderContractInterface.Repository, KoinComponent, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO
    private val mainApiService: MainApiService by inject()

    override suspend fun getPhotosByAlbumId(albumId: Int) = withContext(coroutineContext) {
        try {
            mainApiService.getPhotosByAlbumId(albumId)
        } catch (e: Exception) {
            Log.d("Exception ${javaClass.simpleName}", e.toString())
            mutableListOf<Photo>()
        }
    }
}