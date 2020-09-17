package ru.sulatskov.main.screen.slider

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.viewpager.widget.ViewPager
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_slider.view.*
import ru.sulatskov.common.*
import ru.sulatskov.main.MainActivity
import java.lang.Exception

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
        updateToolbar(getToolbarTitle(), getHasHomeUp())
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoPresenter.attach(this)
        view.save_btn?.setOnClickListener {
            savePhoto(photoPresenter.getUrl(view.photos_vp.currentItem))
        }

        view.current_photo_tv.text =
            String.format("%s %d", getString(R.string.one_from_text), totalCount)
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
                view.current_photo_tv.text = String.format(
                    "%d %s %d",
                    position + 1,
                    getString(R.string.from_text),
                    totalCount
                )
            }
        })

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
            sliderAdapter = SliderImageAdapter(context, photos)
            photos_vp.adapter = sliderAdapter
        }
    }

    override fun getToolbarTitle() = stringProvider.getToolbarNameAlbum()

    override fun getHasHomeUp() = true
}