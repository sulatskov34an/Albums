package ru.sulatskov.main.screen.filters

import ru.sulatskov.base.presenter.BasePresenter

class FiltersPresenter : BasePresenter<FiltersContractInterface.View>(),
    FiltersContractInterface.Presenter {
    override fun onShowClick(sortBy: String?) {
        view?.openGeneralScreen(sortBy)
    }
}