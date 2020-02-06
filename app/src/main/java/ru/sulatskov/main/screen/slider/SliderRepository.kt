package ru.sulatskov.main.screen.slider

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo
import java.lang.Exception

class SliderRepository: SliderContractInterface.Repository, KoinComponent{
    val mainApiService: MainApiService by inject()

    override suspend fun getPhotosByAlbumId(albumId: Int): MutableList<Photo> {
        var photos = mutableListOf<Photo>()
        CoroutineScope(Dispatchers.IO).async {
            try{
                photos = mainApiService.getPhotosByAlbumId(albumId).await()
            }catch (e: Exception){
                Log.d("Exception ${javaClass.simpleName}", e.toString())
            }
        }.await()
        return photos
    }
}