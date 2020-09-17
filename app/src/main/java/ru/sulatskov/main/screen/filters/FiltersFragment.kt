package ru.sulatskov.main.screen.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_filters.view.*
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.StringProvider
import ru.sulatskov.common.updateToolbar
import ru.sulatskov.main.MainActivity

class FiltersFragment : BaseFragment(), FiltersContractInterface.View {

    private val filtersPresenter: FiltersContractInterface.Presenter by inject()
    private val stringProvider: StringProvider by inject()
    private var sort: String = AppConst.SORT_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateToolbar(getToolbarTitle(), getHasHomeUp())
        return inflater.inflate(R.layout.fragment_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.sort_rg.check(R.id.default_rb)
        view.sort_rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.default_rb -> sort = AppConst.SORT_DEFAULT
                R.id.name_asc_rb -> sort = AppConst.SORT_NAME_ACS
                R.id.name_desc_rb -> sort = AppConst.SORT_NAME_DECS
            }
        }
        view.show_btn?.setOnClickListener {
            (activity as? MainActivity)?.openGeneralScreen(sortBy = sort)
        }
        filtersPresenter.attach(this)
    }

    override fun getToolbarTitle() = stringProvider.getToolbarNameMain()

    override fun getHasHomeUp() = true

}