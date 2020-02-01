package ru.sulatskov.main.screen.ptotos

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface

interface PhotosContractInterface {

    interface View : BaseViewInterface {
        fun showError()
        fun showContent()
    }

    interface Presenter : BasePresenterInterface<View> {
        fun todoSome()
    }
}