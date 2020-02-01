package ru.sulatskov.main.screen.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.common.toast
import ru.sulatskov.base.view.BaseFragment
import ru.sulatskov.common.request

class GeneralFragment : BaseFragment(), GeneralContractInterface.View {

    private val generalPresenter: GeneralContractInterface.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generalPresenter.attach(this)
        val view = inflater.inflate(R.layout.fragment_general, container, false)

        request(mainApiService.getAlbums(), true){
            val qwre = it
            var qwreqwre = 2
        }
        return view
    }

    override fun showError() {
        toast("Ошибка загрузки данных")
    }

    override fun showContent() {

    }

}