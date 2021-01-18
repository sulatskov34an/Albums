package ru.sulatskov.main.screen.filters

import ru.sulatskov.base.presenter.BasePresenterInterface
import ru.sulatskov.base.repository.BaseRepositoryInterface
import ru.sulatskov.base.view.BaseViewInterface

interface FiltersContractInterface {

    interface View : BaseViewInterface {
        fun openGeneralScreen(sortBy: String?)
    }

    interface Presenter : BasePresenterInterface<View> {
        fun onShowClick(sortBy: String?)
    }
}