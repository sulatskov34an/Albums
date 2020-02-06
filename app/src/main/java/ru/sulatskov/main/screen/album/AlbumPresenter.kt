package ru.sulatskov.main.screen.album

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.model.network.Photo
import kotlin.coroutines.CoroutineContext

class AlbumPresenter : BasePresenter<AlbumContractInterface.View>(),
    AlbumContractInterface.Presenter, CoroutineScope, KoinComponent {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val albumRepository: AlbumRepository by inject()

    override fun attach(view: AlbumContractInterface.View) {
        var photos: MutableList<Photo>
        super.attach(view)
        launch {
            view.showProgress()
            withContext(Dispatchers.IO){
                photos = albumRepository.getPhotosRemote(view.getAlbumId())
                launch(Dispatchers.Main) {
                    view.hideProgress()
                    if (photos.isEmpty()) {
                        view.showError()
                    } else {
                        view.showContent(photos)
                    }
                }
            }
        }
    }
}