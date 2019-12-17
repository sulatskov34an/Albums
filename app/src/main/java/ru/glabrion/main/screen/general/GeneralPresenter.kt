package ru.glabrion.main.screen.general

import ru.glabrion.base.presenter.BasePresenter

class GeneralPresenter: BasePresenter<GeneralContractInterface.View>(), GeneralContractInterface.Presenter {

    override fun attach(view: GeneralContractInterface.View) {
        super.attach(view)
    }

    override fun todoSome() {

    }

}