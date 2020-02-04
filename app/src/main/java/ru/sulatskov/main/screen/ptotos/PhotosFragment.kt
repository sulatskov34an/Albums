package ru.sulatskov.main.screen.ptotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_photos.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.toast
import ru.sulatskov.main.MainActivity
import ru.sulatskov.model.network.Photo

class PhotosFragment : BaseFragment(), PhotosContractInterface.View {

    private val photosPresenter: PhotosContractInterface.Presenter by inject()

    private var albumId: Int? = 0

    var photosAdapter =
        PhotosAdapter { photo -> (activity as? MainActivity)?.openPhotoScreen(photo.url) }


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
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.photos_rv?.layoutManager = GridLayoutManager(view.context, 2)
        view.photos_rv?.adapter = photosAdapter
        photosPresenter.attach(this)
    }

    override fun showError() {
        toast(getString(R.string.error_load_data_lable))
    }

    override fun showContent(photos: List<Photo>) {
        photosAdapter.setData(photos)
    }

    override fun getAlbumId() = albumId

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }
}