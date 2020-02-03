package ru.sulatskov.main.screen.general

import android.os.Bundle
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
import ru.sulatskov.common.*

class GeneralFragment : BaseFragment(), GeneralContractInterface.View{

    private val generalPresenter: GeneralContractInterface.Presenter by inject()

    var albumsAdapter = AlbumsAdapter{album: Album -> (activity as? MainActivity)?.openPhotosScreen()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generalPresenter.attach(this)
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun onResume() {
        super.onResume()
        view?.albums_rv?.layoutManager = LinearLayoutManager(view?.context)
        view?.albums_rv?.adapter = albumsAdapter
        view?.albums_rv?.addItemDecoration(SimpleDividerItemDecoration(context))
        view?.filter_iv?.setOnClickListener { (activity as? MainActivity)?.openFiltersScreen() }
        request(mainApiService.getAlbums(), true){
            albumsAdapter.setData(it)
        }

    }
    override fun showError() {
        toast("Ошибка загрузки данных")
    }

    override fun showContent() {

    }
}