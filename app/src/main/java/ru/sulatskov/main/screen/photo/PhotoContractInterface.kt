package ru.sulatskov.main.screen.photo

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface

interface PhotoContractInterface {

    interface View : BaseViewInterface {
    }

    interface Presenter : BasePresenterInterface<View> {
    }
}