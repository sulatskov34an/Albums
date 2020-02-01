package ru.sulatskov.main.screen.ptotos

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.sulatskov.base.presenter.BasePresenter
import kotlin.coroutines.CoroutineContext

class PhotosPresenter: BasePresenter<PhotosContractInterface.View>(), PhotosContractInterface.Presenter, CoroutineScope {

    override fun attach(view: PhotosContractInterface.View) {
        super.attach(view)
    }

    override fun todoSome() {

    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}