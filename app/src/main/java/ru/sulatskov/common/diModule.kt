package ru.sulatskov.common

import android.content.Context
import org.koin.dsl.module
import ru.sulatskov.main.screen.filters.FiltersContractInterface
import ru.sulatskov.main.screen.filters.FiltersPresenter
import ru.sulatskov.main.screen.general.GeneralContractInterface
import ru.sulatskov.main.screen.general.GeneralPresenter
import ru.sulatskov.main.screen.photo.PhotoContractInterface
import ru.sulatskov.main.screen.photo.PhotoPresenter
import ru.sulatskov.main.screen.ptotos.PhotosContractInterface
import ru.sulatskov.main.screen.ptotos.PhotosPresenter
import ru.sulatskov.model.db.AlbumsDataBaseService
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.prefs.PrefsService

fun mainModule(context: Context) = module {

    factory <GeneralContractInterface.Presenter> { GeneralPresenter() }

    factory <PhotosContractInterface.Presenter> { PhotosPresenter() }

    factory <PhotoContractInterface.Presenter> { PhotoPresenter() }

    factory <FiltersContractInterface.Presenter> { FiltersPresenter() }

    single { PrefsService(context) }

    single { MainApiService() }

    single { AlbumsDataBaseService(context) }
}