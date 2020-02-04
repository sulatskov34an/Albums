package ru.sulatskov.main.screen.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.downloadFile
import ru.sulatskov.common.toast
import ru.sulatskov.main.MainActivity
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.network.Photo

class SliderFragment : BaseFragment(), SliderContractInterface.View {

    val mainApiService: MainApiService by inject()

    private val photoPresenter: SliderContractInterface.Presenter by inject()
    private var albumId: Int? = 0
    lateinit var sliderAdapter: SlidingImageAdapter
    var photos = listOf<String?>()
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
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            CoroutineScope(Dispatchers.IO).async {
                photos =
                    (mainApiService.getPhotosByAlbumId(albumId).await()).map { photo -> photo.url }

                CoroutineScope(Dispatchers.Main).async {
                   sliderAdapter = SlidingImageAdapter(view.context, photos)
                    view.photos_vp.adapter = sliderAdapter
                }.await()

            }.await()
        }

        view.save_btn?.setOnClickListener {
            val path = photos[view.photos_vp.currentItem]
            var result = downloadFile("$path.jpg")
            if (result != -1L) {
                toast(getString(R.string.save_success_text))
            } else {
                toast(getString(R.string.save_fail_text))
            }
        }

         photoPresenter.attach(this)
    }

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }
}