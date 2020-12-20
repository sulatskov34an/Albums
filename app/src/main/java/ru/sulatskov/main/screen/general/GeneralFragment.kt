package ru.sulatskov.main.screen.general

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_filters.*
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import ru.sulatskov.main.MainActivity
import ru.sulatskov.model.network.Album
import kotlinx.android.synthetic.main.fragment_general.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sulatskov.common.*

class GeneralFragment : BaseFragment(), GeneralContractInterface.View, TextWatcher {

    private val generalPresenter: GeneralContractInterface.Presenter by inject()
    private val stringProvider: StringProvider by inject()
    private var sortBy: String? = AppConst.SORT_DEFAULT
    private var albumsAdapter =
        AlbumsAdapter { album: Album -> generalPresenter.onAlbumClick(album.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sortBy = arguments?.getString(AppConst.SORT_KEY, AppConst.SORT_DEFAULT)
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.albums_rv?.layoutManager = LinearLayoutManager(view.context)
        view.albums_rv?.adapter = albumsAdapter
        view.albums_rv?.addItemDecoration(SimpleDividerItemDecoration(context))
        view.filter_iv?.setOnClickListener { (activity as? MainActivity)?.openFiltersScreen() }
        view.swipe_container?.setOnRefreshListener {
            (activity as? MainActivity)?.openGeneralScreen(
                sortBy = AppConst.SORT_DEFAULT
            )
        }
        view.filter_search_et.addTextChangedListener(this)
        view.apply {
            (activity as? MainActivity)?.window
                ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
        generalPresenter.attach(this)
        when (sortBy) {
            AppConst.SORT_DEFAULT -> generalPresenter.getData(AppConst.SORT_DEFAULT)
            AppConst.SORT_NAME_ACS -> generalPresenter.getData(AppConst.SORT_NAME_ACS)
            AppConst.SORT_NAME_DECS -> generalPresenter.getData(AppConst.SORT_NAME_DECS)
        }
    }

    override fun showError() {
        toast(getString(R.string.error_load_data_label))
    }

    override fun setData(albums: List<Album>) {
        albumsAdapter.setData(albums)
    }

    override fun openAlbum(id: Int?) {
        (activity as? MainActivity)?.openAlbumScreen(albumId = id)
    }

    override fun initToolbar() {
        toolbar_title.text = stringProvider.getToolbarNameMain()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        launch {
            delay(1000)
            generalPresenter.onTextChanged(albumsAdapter.getList(), s)
        }
        if (s.isNullOrEmpty()) {
            generalPresenter.getData(AppConst.SORT_DEFAULT)
        }
    }


}