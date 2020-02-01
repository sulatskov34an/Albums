package ru.sulatskov.main.screen.ptotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import ru.sulatskov.common.request
import kotlinx.android.synthetic.main.fragment_photos.view.*
import ru.sulatskov.common.SimpleDividerItemDecoration

class PhotosFragment : BaseFragment(), PhotosContractInterface.View {

    private val photosPresenter: PhotosContractInterface.Presenter by inject()

    var photosAdapter = PhotosAdapter{}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photosPresenter.attach(this)
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onResume() {
        super.onResume()
        view?.photos_rv?.layoutManager = GridLayoutManager(view?.context, 2)
        view?.photos_rv?.adapter = photosAdapter
        request(mainApiService.getPhotosByAlbumId(1), true){
            photosAdapter.setData(it)
        }
    }

    override fun showError() {

    }

    override fun showContent() {

    }
}