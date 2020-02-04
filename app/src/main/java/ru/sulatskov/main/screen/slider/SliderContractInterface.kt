package ru.sulatskov.main.screen.slider

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface

interface SliderContractInterface {

    interface View : BaseViewInterface {
    }

    interface Presenter : BasePresenterInterface<View> {
    }
}