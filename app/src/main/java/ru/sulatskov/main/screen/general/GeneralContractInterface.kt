package ru.sulatskov.main.screen.general

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.repository.BaseRepositoryInterface
import ru.sulatskov.base.view.BaseViewInterface
import ru.sulatskov.model.db.entity.AlbumEntity
import ru.sulatskov.model.network.Album
import java.lang.Exception

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