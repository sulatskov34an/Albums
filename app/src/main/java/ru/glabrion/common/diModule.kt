package ru.glabrion.common

import android.content.Context
import org.koin.dsl.module
import ru.glabrion.main.screen.general.GeneralContractInterface
import ru.glabrion.main.screen.general.GeneralPresenter
import ru.glabrion.model.network.MainApiService
import ru.glabrion.model.prefs.PrefsService

fun mainModule(context: Context) = module {

    single <GeneralContractInterface.Presenter> { GeneralPresenter() }

    single { PrefsService(context) }
    single { MainApiService() }
}