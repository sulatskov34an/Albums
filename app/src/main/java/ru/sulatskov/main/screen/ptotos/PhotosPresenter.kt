package ru.sulatskov.main.screen.ptotos

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo
import kotlin.coroutines.CoroutineContext

class PhotosPresenter: BasePresenter<PhotosContractInterface.View>(), PhotosContractInterface.Presenter, CoroutineScope, KoinComponent {

    val mainApiService: MainApiService by inject()

    override fun attach(view: PhotosContractInterface.View) {
        super.attach(view)
    }

    override suspend fun getPhotos(albumId: Int?): MutableList<Photo> {
         var photos = mutableListOf<Photo>()
        async {
            try {
                photos = mainApiService.getPhotosByAlbumId(albumId = albumId).await()
                return@async photos
            }catch (e: Exception){
                return@async photos
            }
        }.await()
        return photos
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}