package ru.sulatskov.main.screen.general

import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.model.network.Album
import ru.sulatskov.model.network.MainApiService
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class GeneralPresenter: BasePresenter<GeneralContractInterface.View>(), GeneralContractInterface.Presenter, CoroutineScope, KoinComponent {

    val mainApiService: MainApiService by inject()

    override fun attach(view: GeneralContractInterface.View) {
        super.attach(view)
    }

    override suspend fun getAlbums(): MutableList<Album> {
        var albums = mutableListOf<Album>()
        async {
            try {
                albums = mainApiService.getAlbums().await()
                return@async albums
            }catch (e: Exception){
                return@async albums
            }
        }.await()
        return albums
    }


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}