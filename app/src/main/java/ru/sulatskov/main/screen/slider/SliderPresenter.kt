package ru.sulatskov.main.screen.slider

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo
import kotlin.coroutines.CoroutineContext

class SliderPresenter : BasePresenter<SliderContractInterface.View>(),
    SliderContractInterface.Presenter, CoroutineScope, KoinComponent {

    val mainApiService: MainApiService by inject()
    var photos = listOf<String?>()
    override fun attach(view: SliderContractInterface.View) {
        super.attach(view)

        launch {
            CoroutineScope(Dispatchers.IO).async {
                photos = getPhotosByAlbumId(view.getAlbumId()).map { photo ->
                    photo.url
                }
                CoroutineScope(Dispatchers.Main).async {
                    view.showPhotos(photos)
                }.await()
            }.await()
        }
    }

    override fun getUrl(position: Int) = photos[position]

    override fun getTotalCount(): Int = photos.size

    suspend fun getPhotosByAlbumId(albumId: Int): MutableList<Photo> {
        var photos = mutableListOf<Photo>()
        CoroutineScope(Dispatchers.IO).async {

            photos = mainApiService.getPhotosByAlbumId(albumId).await()
        }.await()
        return photos
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}