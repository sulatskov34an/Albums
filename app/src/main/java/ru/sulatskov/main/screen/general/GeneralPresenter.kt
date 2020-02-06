package ru.sulatskov.main.screen.general

import android.util.Log
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.ConnectionProvider
import ru.sulatskov.main.screen.slider.SliderRepository
import ru.sulatskov.model.db.AlbumsDataBaseService
import ru.sulatskov.model.db.entity.AlbumEntity
import ru.sulatskov.model.network.Album
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.prefs.PrefsService
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class GeneralPresenter : BasePresenter<GeneralContractInterface.View>(),
    GeneralContractInterface.Presenter, CoroutineScope, KoinComponent {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val prefsService: PrefsService by inject()
    val connection: ConnectionProvider by inject()
    private val generalRepository : GeneralRepository by inject()

    override fun sortBy(sort: String) {
        var albums = mutableListOf<Album>()
        launch {
            view?.showProgress()
            CoroutineScope(Dispatchers.Default).async {
                if (connection.isConnected()) {
                    albums = generalRepository.getAlbumsRemote()
                    generalRepository.insertAlbums(albums)
                    prefsService.hasDB = true

                } else {
                    if (prefsService.hasDB) {
                        albums = generalRepository.getAlbumsDB()
                    }
                }
                CoroutineScope(Dispatchers.Main).async {
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
                }.await()
            }.await()
        }
    }

    override fun attach(view: GeneralContractInterface.View) {
        super.attach(view)
    }
}