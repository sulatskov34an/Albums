package ru.sulatskov.main.screen.general

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.ConnectionProvider
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

    val mainApiService: MainApiService by inject()
    val dbService: AlbumsDataBaseService by inject()
    val prefsService: PrefsService by inject()
    val connection: ConnectionProvider by inject()

    override fun sortBy(sort: String) {
        var albums = mutableListOf<Album>()
        launch {
            view?.showProgress()
            CoroutineScope(Dispatchers.Default).async {
                if (connection.isConnected()) {
                    albums = getAlbumsRemote()
                    insertAlbums(albums)
                    prefsService.hasDB = true

                } else {
                    if (prefsService.hasDB) {
                        albums = getAlbumsDB()
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

    private suspend fun getAlbumsRemote(): MutableList<Album> {
        var albums = mutableListOf<Album>()
        CoroutineScope(Dispatchers.Default).async {
            try {
                albums = mainApiService.getAlbums().await()
                return@async albums
            } catch (e: Exception) {
                return@async albums
            }
        }.await()
        return albums
    }

    private suspend fun getAlbumsDB(): MutableList<Album> {
        var albums = mutableListOf<Album>()
        CoroutineScope(Dispatchers.Default).async {
            try {
                albums = dbService.getAllAlbums().map { album ->
                    Album(
                        id = album.id,
                        title = album.title,
                        userId = album.userId
                    )
                }.toMutableList()
                return@async albums
            } catch (e: Exception) {
                return@async albums
            }
        }.await()
        return albums
    }

    private fun insertAlbums(albums: MutableList<Album>) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                dbService.insertAlbumsList(albums = albums.map { album ->
                    AlbumEntity(
                        id = album.id,
                        userId = album.userId,
                        title = album.title
                    )
                })

            } catch (e: Exception) {

            }
        }
    }
}