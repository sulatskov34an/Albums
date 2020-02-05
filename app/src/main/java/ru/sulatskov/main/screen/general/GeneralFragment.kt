package ru.sulatskov.main.screen.general

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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

    private var albumsAdapter =
        AlbumsAdapter { album: Album -> (activity as? MainActivity)?.openAlbumScreen(albumId = album.id) }

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
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.albums_rv?.layoutManager = LinearLayoutManager(view?.context)
        view.albums_rv?.adapter = albumsAdapter
        view.albums_rv?.addItemDecoration(SimpleDividerItemDecoration(context))
        view.filter_iv?.setOnClickListener { (activity as? MainActivity)?.openFiltersScreen() }
        view.swipe_container?.setOnRefreshListener { (activity as? MainActivity)?.openGeneralScreen() }
        view.filter_search_et.addTextChangedListener(this)
        generalPresenter.attach(this)

    }

    override fun showError() {
        toast(getString(R.string.error_load_data_label))
    }

    override fun showContent(albums: List<Album>) {
        albumsAdapter.setData(albums)
    }

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }

    override fun getToolbarTitle() = "Главная"

    override fun getHasHomeUp() = false

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        launch {
            delay(1000)
            val list = albumsAdapter.getList()
            val sortedList = mutableListOf<Album>()
            for(item in list)
            if(item.title?.contains(s.toString()) == true){
                sortedList.add(item)
            }
            albumsAdapter.setData(sortedList)
        }
        if(s.isNullOrEmpty()){
            generalPresenter.attach(this)
        }
    }


}