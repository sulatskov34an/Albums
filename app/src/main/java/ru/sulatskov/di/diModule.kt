package ru.sulatskov.di

import android.content.Context
import org.koin.dsl.module
import ru.sulatskov.common.ConnectionProvider
import ru.sulatskov.common.providers.StringProvider
import ru.sulatskov.main.screen.general.GeneralContractInterface
import ru.sulatskov.main.screen.general.GeneralPresenter
import ru.sulatskov.main.screen.slider.SliderContractInterface
import ru.sulatskov.main.screen.slider.SliderPresenter
import ru.sulatskov.main.screen.album.AlbumContractInterface
import ru.sulatskov.main.screen.album.AlbumPresenter
import ru.sulatskov.main.screen.album.AlbumRepository
import ru.sulatskov.main.screen.filters.FiltersContractInterface
import ru.sulatskov.main.screen.filters.FiltersPresenter
import ru.sulatskov.main.screen.general.GeneralRepository
import ru.sulatskov.main.screen.slider.SliderRepository
import ru.sulatskov.model.db.AlbumsDataBaseService
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.prefs.PrefsService

fun mainModule(context: Context) = module {

    single <GeneralContractInterface.Presenter> { GeneralPresenter( ) }

    single <FiltersContractInterface.Presenter> { FiltersPresenter( ) }

    single <AlbumContractInterface.Presenter> { AlbumPresenter() }

    single <SliderContractInterface.Presenter> { SliderPresenter() }

    single { PrefsService(context) }

    single { MainApiService() }

    single { AlbumsDataBaseService(context) }

    single { ConnectionProvider(context) }

    single { AlbumRepository() }

    single { GeneralRepository() }

    single { SliderRepository() }

    single { StringProvider(context) }

}