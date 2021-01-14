package ru.sulatskov.main.screen.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_album.view.*
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.providers.StringProvider
import ru.sulatskov.common.toast
import ru.sulatskov.main.MainActivity
import ru.sulatskov.model.network.Photo

class AlbumFragment : BaseFragment(), AlbumContractInterface.View {

    private val albumPresenter: AlbumContractInterface.Presenter by inject()
    private val stringProvider: StringProvider by inject()

    private var albumId: Int? = 0
    private var photosCount: Int? = 0
    private var albumAdapter =
        AlbumAdapter { photo ->
            (activity as? MainActivity)?.openSliderScreen(
                photo.albumId,
                photosCount
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        albumId = arguments?.getInt(AppConst.ID_ALBUM_KEY, 0)
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.photos_rv?.layoutManager = GridLayoutManager(view.context, 2)
        view.photos_rv?.adapter = albumAdapter
        view.swipe_container.setOnRefreshListener {
            (activity as? MainActivity)?.openAlbumScreen(
                albumId
            )
        }
        albumPresenter.attach(this)
    }

    override fun showError() {
        toast(getString(R.string.error_load_data_label))
    }

    override fun setData(photos: List<Photo>) {
        photosCount = photos.size
        albumAdapter.setData(photos)
    }

    override fun getAlbumId() = albumId

    override fun initToolbar() {
        view?.toolbar_title?.text = stringProvider.getToolbarNameAlbum()
        view?.toolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}