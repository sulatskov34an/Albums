package ru.sulatskov.main.screen.slider

import android.os.Bundle
import android.view.*
import androidx.viewpager.widget.ViewPager
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_slider.view.*
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.downloadFile
import ru.sulatskov.common.toast
import ru.sulatskov.common.updateToolbar
import ru.sulatskov.main.MainActivity

class SliderFragment : BaseFragment(), SliderContractInterface.View {

    private val photoPresenter: SliderContractInterface.Presenter by inject()
    private var albumId: Int? = 0
    private var totalCount: Int? = 0
    lateinit var sliderAdapter: SliderImageAdapter
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
        totalCount = arguments?.getInt(AppConst.PHOTOS_COUNT_KEY, 0)
        updateToolbar(getToolbarTitle(), getHasHomeUp())
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoPresenter.attach(this)
        view.save_btn?.setOnClickListener {
            savePhoto(photoPresenter.getUrl(view.photos_vp.currentItem))
        }

        view.current_photo_tv.setText("1 из $totalCount")
        view.swipe_container.setOnRefreshListener { (activity as MainActivity).openSliderScreen(albumId, totalCount) }
        view.photos_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                view.current_photo_tv.setText("${position + 1} из $totalCount")
            }
        })

    }

    fun savePhoto(path: String?) {
        val result = downloadFile("$path.jpg")
        if (result != -1L) {
            toast(getString(R.string.save_success_text))
        } else {
            toast(getString(R.string.save_fail_text))
        }
    }

    override fun getAlbumId(): Int = albumId ?: 0

    override fun showPhotos(photos: List<String?>) {
        view?.apply {
            sliderAdapter = SliderImageAdapter(context, photos)
            photos_vp.setAdapter(sliderAdapter)
        }

    }

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }

    override fun getToolbarTitle() = "Альбом"

    override fun getHasHomeUp() = true
}