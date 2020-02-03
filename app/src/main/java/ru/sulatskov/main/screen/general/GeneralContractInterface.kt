package ru.sulatskov.main.screen.general

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface

interface GeneralContractInterface {

    interface View : BaseViewInterface {
        fun showError()
        fun showContent()
    }

    interface Presenter : BasePresenterInterface<View> {
        fun todoSome()
    }
}