package ru.sulatskov.main.screen.filters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.sulatskov.base.presenter.BasePresenter
import kotlin.coroutines.CoroutineContext

class FiltersPresenter: BasePresenter<FiltersContractInterface.View>(), FiltersContractInterface.Presenter, CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
}