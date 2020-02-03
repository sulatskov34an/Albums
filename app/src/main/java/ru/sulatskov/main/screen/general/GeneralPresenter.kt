package ru.sulatskov.main.screen.general

import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.model.db.AlbumsDataBaseService
import ru.sulatskov.model.db.entity.AlbumEntity
import ru.sulatskov.model.network.Album
import ru.sulatskov.model.network.MainApiService
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class GeneralPresenter : BasePresenter<GeneralContractInterface.View>(),
    GeneralContractInterface.Presenter, CoroutineScope, KoinComponent {

    val mainApiService: MainApiService by inject()
    val dbService: AlbumsDataBaseService by inject()

    override fun attach(view: GeneralContractInterface.View) {
        super.attach(view)
    }

    override suspend fun getAlbumsRemote(): MutableList<Album> {
        var albums = mutableListOf<Album>()
        async {
            try {
                albums = mainApiService.getAlbums().await()
                return@async albums
            } catch (e: Exception) {
                return@async albums
            }
        }.await()
        return albums
    }

    override suspend fun getAlbumsDB(): MutableList<Album> {
        var albums = mutableListOf<Album>()
        async {
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

    override fun insertAlbums(albums: MutableList<Album>) {
        launch {
            dbService.insertAlbumsList(albums = albums.map { album ->
                AlbumEntity(
                    id = album.id,
                    userId = album.userId,
                    title = album.title
                )
            })
        }
    }


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}