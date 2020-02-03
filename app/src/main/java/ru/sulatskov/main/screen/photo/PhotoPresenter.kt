package ru.sulatskov.main.screen.photo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.sulatskov.base.presenter.BasePresenter
import kotlin.coroutines.CoroutineContext

class PhotoPresenter: BasePresenter<PhotoContractInterface.View>(), PhotoContractInterface.Presenter, CoroutineScope {

    override fun attach(view: PhotoContractInterface.View) {
        super.attach(view)
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}