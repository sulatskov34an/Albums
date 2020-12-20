package ru.sulatskov.main.screen.general

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.repository.BaseRepositoryInterface
import ru.sulatskov.base.view.BaseViewInterface
import ru.sulatskov.model.network.Album

interface GeneralContractInterface {

    interface View : BaseViewInterface {
        fun showError()
        fun setData(albums: List<Album>)
        fun openAlbum(id: Int?)
    }

    interface Presenter : BasePresenterInterface<View> {
        fun getData(sortBy: String)
        fun onTextChanged(list: MutableList<Album>, s: CharSequence?)
        fun onAlbumClick(id: Int?)
    }

    interface Repository : BaseRepositoryInterface {
        suspend fun getAlbumsRemote(): MutableList<Album>
        suspend fun getAlbumsFromCache(): MutableList<Album>
        fun insertAlbums(albums: MutableList<Album>)
    }
}