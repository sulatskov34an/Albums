package ru.sulatskov.main.screen.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_album.view.*
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.toast
import ru.sulatskov.common.updateToolbar
import ru.sulatskov.main.MainActivity
import ru.sulatskov.model.network.Photo

class AlbumFragment : BaseFragment(), AlbumContractInterface.View {

    private val albumPresenter: AlbumContractInterface.Presenter by inject()

    private var albumId: Int? = 0
    private var photosCount: Int? = 0

    var albumAdapter =
        AlbumAdapter { photo -> (activity as? MainActivity)?.openSliderScreen(photo.albumId, photosCount) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        albumId = arguments?.getInt(AppConst.ID_ALBUM_KEY, 0)
        updateToolbar(getToolbarTitle(), getHasHomeUp())
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.photos_rv?.layoutManager = GridLayoutManager(view.context, 2)
        view.photos_rv?.adapter = albumAdapter
        view.swipe_container.setOnRefreshListener { (activity as? MainActivity)?.openAlbumScreen(albumId) }
        albumPresenter.attach(this)
    }

    override fun showError() {
        toast(getString(R.string.error_load_data_label))
    }

    override fun showContent(photos: List<Photo>) {
        photosCount = photos.size
        albumAdapter.setData(photos)
    }

    override fun getAlbumId() = albumId

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }

    override fun getToolbarTitle() = "Главная"

    override fun getHasHomeUp() = true
}