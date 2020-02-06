package ru.sulatskov.main.screen.slider

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import kotlin.coroutines.CoroutineContext

class SliderPresenter : BasePresenter<SliderContractInterface.View>(),
    SliderContractInterface.Presenter, CoroutineScope, KoinComponent {

    private val sliderRepository : SliderRepository by inject()
    var photos = listOf<String?>()
    override fun attach(view: SliderContractInterface.View) {
        super.attach(view)

        launch {
            CoroutineScope(Dispatchers.IO).async {
                photos = sliderRepository.getPhotosByAlbumId(view.getAlbumId()).map { photo ->
                    photo.url
                }
                CoroutineScope(Dispatchers.Main).async {
                    view.showPhotos(photos)
                }.await()
            }.await()
        }
    }

    override fun getUrl(position: Int) = photos[position]

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}