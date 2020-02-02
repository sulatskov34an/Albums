package ru.sulatskov.common

import android.content.Context
import org.koin.dsl.module
import ru.sulatskov.main.screen.general.GeneralPresenter
import ru.sulatskov.main.screen.ptotos.PhotosContractInterface
import ru.sulatskov.main.screen.ptotos.PhotosPresenter
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.prefs.PrefsService

fun mainModule(context: Context) = module {

    factory <GeneralContractInterface.Presenter> { GeneralPresenter() }

    factory <PhotosContractInterface.Presenter> { PhotosPresenter() }

    single { PrefsService(context) }
    single { MainApiService() }
}