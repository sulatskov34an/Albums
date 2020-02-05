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
import ru.sulatskov.common.updateToolbar
import ru.sulatskov.main.MainActivity

class FiltersFragment : BaseFragment(), FiltersContractInterface.View {

    private val filtersPresenter: FiltersContractInterface.Presenter by inject()
    private var check: String = AppConst.SORT_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
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
        view.close_iv?.setOnClickListener { (activity as? MainActivity)?.openGeneralScreen(sortBy = AppConst.SORT_DEFAULT) }
        view.sort_rg.check(R.id.default_rb)
        view.sort_rg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.default_rb -> check = AppConst.SORT_DEFAULT
                    R.id.name_asc_rb -> check = AppConst.SORT_NAME_ACS
                    R.id.name_desc_rb -> check = AppConst.SORT_NAME_DECS
                }
            }
        })
        view.show_btn?.setOnClickListener {
            (activity as? MainActivity)?.openGeneralScreen(sortBy = check)
        }
        filtersPresenter.attach(this)
    }

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }

    override fun getToolbarTitle() = "Главная"

    override fun getHasHomeUp() = true

}