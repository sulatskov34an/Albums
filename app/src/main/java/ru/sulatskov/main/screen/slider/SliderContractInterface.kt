package ru.sulatskov.main.screen.slider

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.repository.BaseRepositoryInterface
import ru.sulatskov.base.view.BaseViewInterface
import ru.sulatskov.model.network.Photo

interface SliderContractInterface {

    interface View : BaseViewInterface {
        fun getAlbumId(): Int
        fun setData(photos: List<String?>)
    }

    interface Presenter : BasePresenterInterface<View> {
        fun getUrl(position: Int): String?
    }
    interface Repository: BaseRepositoryInterface{
        suspend fun getPhotosByAlbumId(albumId: Int): MutableList<Photo>
    }
}