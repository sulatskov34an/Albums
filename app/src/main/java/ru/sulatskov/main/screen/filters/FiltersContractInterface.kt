package ru.sulatskov.main.screen.filters

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.view.BaseViewInterface

interface FiltersContractInterface {

    interface View : BaseViewInterface {

    }

    interface Presenter : BasePresenterInterface<View> {

    }
}