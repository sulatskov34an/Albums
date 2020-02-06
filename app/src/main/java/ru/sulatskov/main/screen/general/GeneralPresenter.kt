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

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val prefsService: PrefsService by inject()
    private val connection: ConnectionProvider by inject()
    private val generalRepository: GeneralRepository by inject()

    override fun sortBy(sort: String) {
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
                        albums = generalRepository.getAlbumsDB()
                    }
                }
            }
            withContext(Dispatchers.Main) {
                view?.hideProgress()
                if (albums.isEmpty()) {
                    view?.showError()
                } else {
                    when (sort) {
                        AppConst.SORT_DEFAULT -> view?.showContent(albums)
                        AppConst.SORT_NAME_ACS -> view?.showContent(albums.sortedBy { it.title })
                        AppConst.SORT_NAME_DECS -> view?.showContent(albums.sortedByDescending { it.title })
                        else -> view?.showContent(albums)
                    }

                }
            }
        }
    }
}