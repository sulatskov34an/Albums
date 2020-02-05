package ru.sulatskov.main.screen.album

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo
import kotlin.coroutines.CoroutineContext

class AlbumPresenter: BasePresenter<AlbumContractInterface.View>(), AlbumContractInterface.Presenter, CoroutineScope, KoinComponent {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val mainApiService: MainApiService by inject()

    override fun attach(view: AlbumContractInterface.View) {
        var photos = mutableListOf<Photo>()
        super.attach(view)
        launch {
            view.showProgress()
            CoroutineScope(Dispatchers.Default).async {
                photos = getPhotosRemote(view.getAlbumId())
                CoroutineScope(Dispatchers.Main).async {
                    view.hideProgress()
                    if (photos.isEmpty()) {
                        view.showError()
                    } else {
                        view.showContent(photos)
                    }
                }.await()
            }.await()
        }
    }

    private suspend fun getPhotosRemote(albumId: Int?): MutableList<Photo> {
         var photos = mutableListOf<Photo>()
        CoroutineScope(Dispatchers.Default).async {
            try {
                photos = mainApiService.getPhotosByAlbumId(albumId = albumId).await()
                return@async photos
            }catch (e: Exception){
                return@async photos
            }
        }.await()
        return photos
    }
}