package ru.sulatskov.main.screen.slider

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import kotlin.coroutines.CoroutineContext

class SliderPresenter : BasePresenter<SliderContractInterface.View>(),
    SliderContractInterface.Presenter, CoroutineScope, KoinComponent {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val sliderRepository: SliderRepository by inject()
    var photos = listOf<String?>()
    override fun attach(view: SliderContractInterface.View) {
        super.attach(view)

        launch {
            photos = sliderRepository.getPhotosByAlbumId(view.getAlbumId()).map { photo ->
                photo.url
            }
            launch(Dispatchers.Main) {
                view.showPhotos(photos)
            }
        }
    }

    override fun getUrl(position: Int) = photos[position]
}