package ru.sulatskov.main.screen.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo.view.*
import kotlinx.android.synthetic.main.fragment_photo.view.photo_iv
import kotlinx.android.synthetic.main.item_photo.view.*
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.getProgressBar

class PhotoFragment : BaseFragment(), PhotoContractInterface.View {

    private val photoPresenter: PhotoContractInterface.Presenter by inject()
    private var url: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photoPresenter.attach(this)
        url = arguments?.getString(AppConst.ID_URL_KEY, "")
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onResume() {
        super.onResume()

        view?.photo_iv?.apply {
            val path = url+".jpg"
            Glide.with(this)
                .load(path)
                .error(R.drawable.error)
                .placeholder(getProgressBar(context))
                .into(photo_iv)
        }
    }
}