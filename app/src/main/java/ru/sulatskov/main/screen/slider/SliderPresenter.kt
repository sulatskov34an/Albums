package ru.sulatskov.main.screen.slider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.sulatskov.base.presenter.BasePresenter
import kotlin.coroutines.CoroutineContext

class SliderPresenter: BasePresenter<SliderContractInterface.View>(), SliderContractInterface.Presenter, CoroutineScope {

    override fun attach(view: SliderContractInterface.View) {
        super.attach(view)
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}