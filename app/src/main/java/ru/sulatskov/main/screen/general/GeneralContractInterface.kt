package ru.sulatskov.main.screen.general

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.repository.BaseRepositoryInterface
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

    interface Repository : BaseRepositoryInterface {
        suspend fun getAlbumsRemote(): MutableList<Album>
        suspend fun getAlbumsDB(): MutableList<Album>
        fun insertAlbums(albums: MutableList<Album>)
    }
}