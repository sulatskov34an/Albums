package ru.sulatskov.main.screen.general

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface
import ru.sulatskov.model.network.Album

interface GeneralContractInterface {

    interface View : BaseViewInterface {
        fun showError()
        fun showContent(albums: List<Album>)
    }

    interface Presenter : BasePresenterInterface<View> {
        fun sortBy(sort: String)
    }
}