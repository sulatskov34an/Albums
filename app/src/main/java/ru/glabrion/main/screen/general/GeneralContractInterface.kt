package ru.glabrion.main.screen.general

import ru.glabrion.base.presenter.BasePresenterInterface
import ru.glabrion.base.view.BaseViewInterface

interface GeneralContractInterface {

    interface View : BaseViewInterface {
        fun showError()
        fun showContent()
    }

    interface Presenter : BasePresenterInterface<View> {
        fun todoSome()
    }
}