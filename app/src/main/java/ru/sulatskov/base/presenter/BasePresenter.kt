package ru.sulatskov.base.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.sulatskov.base.view.BaseViewInterface
import kotlin.coroutines.CoroutineContext

open class BasePresenter<V: BaseViewInterface> : BasePresenterInterface<V>, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    var view: V? = null

    override val isAttached: Boolean = view != null

    override fun attach(view :V) {
        this.view = view
    }

    override fun detach() {
        view = null
    }
}