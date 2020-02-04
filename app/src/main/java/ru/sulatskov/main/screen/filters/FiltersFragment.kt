package ru.sulatskov.main.screen.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_filters.view.*
import ru.sulatskov.main.MainActivity

class FiltersFragment : BaseFragment(), FiltersContractInterface.View{

    private val filtersPresenter: FiltersContractInterface.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.close_iv?.setOnClickListener { (activity as? MainActivity)?.openGeneralScreen() }
        view.show_btn?.setOnClickListener { (activity as? MainActivity)?.openGeneralScreen() }
        filtersPresenter.attach(this)
    }

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }

}