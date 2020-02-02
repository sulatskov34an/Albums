package ru.sulatskov.main.screen.general

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.sulatskov.base.presenter.BasePresenter
import ru.sulatskov.common.GeneralContractInterface
import kotlin.coroutines.CoroutineContext

class GeneralPresenter: BasePresenter<GeneralContractInterface.View>(), GeneralContractInterface.Presenter, CoroutineScope {

    override fun attach(view: GeneralContractInterface.View) {
        super.attach(view)
    }

    override fun todoSome() {

    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}