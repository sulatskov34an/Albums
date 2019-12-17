package ru.glabrion.base.presenter

import ru.glabrion.base.view.BaseViewInterface

interface BasePresenterInterface<V: BaseViewInterface> {

    val isAttached: Boolean

    fun attach(view: V)

    fun detach()

}