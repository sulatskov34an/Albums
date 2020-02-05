package ru.sulatskov.main.screen.slider

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface
import java.text.FieldPosition

interface SliderContractInterface {

    interface View : BaseViewInterface {
        fun getAlbumId(): Int
        fun showPhotos(photos: List<String?>)
    }

    interface Presenter : BasePresenterInterface<View> {
        fun getUrl(position: Int): String?
        fun getTotalCount(): Int
    }
}