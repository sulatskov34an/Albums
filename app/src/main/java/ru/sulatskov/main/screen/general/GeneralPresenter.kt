package ru.sulatskov.main.screen.general

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.ConnectionProvider
import ru.sulatskov.model.network.Album
import ru.sulatskov.model.prefs.PrefsService
import kotlin.coroutines.CoroutineContext

class GeneralPresenter : BasePresenter<GeneralContractInterface.View>(),
    GeneralContractInterface.Presenter, CoroutineScope, KoinComponent {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO

    private val prefsService: PrefsService by inject()
    private val connection: ConnectionProvider by inject()
    private val generalRepository: GeneralRepository by inject()

    override fun getData(sortBy: String) {
        var albums = mutableListOf<Album>()
        view?.showProgress()
        launch {
            withContext(coroutineContext) {
                if (connection.isConnected()) {
                    albums = generalRepository.getAlbumsRemote()
                    generalRepository.insertAlbums(albums)
                    prefsService.hasDB = true

                } else {
                    if (prefsService.hasDB) {
                        albums = generalRepository.getAlbumsFromCache()
                    }
                }
            }
            withContext(Dispatchers.Main) {
                view?.hideProgress()
                if (albums.isEmpty()) {
                    view?.showError()
                } else {
                    when (sortBy) {
                        AppConst.SORT_DEFAULT -> view?.setData(albums)
                        AppConst.SORT_NAME_ACS -> view?.setData(albums.sortedBy { it.title })
                        AppConst.SORT_NAME_DECS -> view?.setData(albums.sortedByDescending { it.title })
                        else -> view?.setData(albums)
                    }

                }
            }
        }
    }

    override fun onTextChanged(list: MutableList<Album>, s: CharSequence?) {
        val sortedList = mutableListOf<Album>()
        for (item in list)
            item.title?.let {
                if (it.contains(s.toString()))
                    sortedList.add(item)
            }
        view?.setData(sortedList)
    }

    override fun onAlbumClick(id: Int?) {
        view?.openAlbum(id)
    }
}