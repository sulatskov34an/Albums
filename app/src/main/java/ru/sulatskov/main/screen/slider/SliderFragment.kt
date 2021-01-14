package ru.sulatskov.main.screen.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_filters.*
import kotlinx.android.synthetic.main.fragment_slider.view.*
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import ru.sulatskov.common.*


class SliderFragment : BaseFragment(), SliderContractInterface.View {

    private val photoPresenter: SliderContractInterface.Presenter by inject()
    private val stringProvider: StringProvider by inject()
    private var albumId: Int? = 0
    private var totalCount: Int? = 0
    private lateinit var sliderAdapter: SliderImageAdapter
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
        totalCount = arguments?.getInt(AppConst.PHOTOS_COUNT_KEY, 0)
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoPresenter.attach(this)
        view.save_btn?.setOnClickListener {
            val layoutManager = view.photos_rv.layoutManager as LinearLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            savePhoto(photoPresenter.getUrl(firstVisiblePosition))
        }

        view.photos_rv?.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        view.photos_rv.addItemDecoration(SliderItemDecorator(context))

    }

    private fun savePhoto(path: String?) {
        try {
            val result = downloadFile("$path.jpg")
            if (result != -1L) {
                toast(getString(R.string.save_success_text))
            } else {
                toast(getString(R.string.save_fail_text))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            toast(getString(R.string.error_save))
        }
    }

    override fun getAlbumId(): Int = albumId ?: 0

    override fun setData(photos: List<String?>) {
        view?.apply {
            sliderAdapter = SliderImageAdapter(context)
            photos_rv.adapter = sliderAdapter
            sliderAdapter.setData(photos.takeLast(10))
        }
    }

    override fun initToolbar() {
        toolbar_title.text = stringProvider.getToolbarNameAlbum()
    }
}