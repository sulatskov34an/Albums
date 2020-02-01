package ru.sulatskov.common

import android.content.Context
import org.koin.dsl.module
import ru.sulatskov.main.screen.general.GeneralContractInterface
import ru.sulatskov.main.screen.general.GeneralPresenter
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.prefs.PrefsService

fun mainModule(context: Context) = module {

    factory <GeneralContractInterface.Presenter> { GeneralPresenter() }

    single { PrefsService(context) }
    single { MainApiService() }
}