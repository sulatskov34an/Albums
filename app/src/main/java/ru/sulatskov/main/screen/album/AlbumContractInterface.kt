package ru.sulatskov.main.screen.album

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface
import ru.sulatskov.model.network.Photo

interface AlbumContractInterface {

    interface View : BaseViewInterface {
        fun showError()
        fun showContent(photos: List<Photo>)
        fun getAlbumId(): Int?
    }

    interface Presenter : BasePresenterInterface<View> {
    }
}