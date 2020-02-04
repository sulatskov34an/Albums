package ru.sulatskov.main.screen.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo.view.*
import kotlinx.android.synthetic.main.fragment_photo.view.photo_iv
import ru.sulatskov.common.AppConst
import ru.sulatskov.common.downloadFile
import ru.sulatskov.common.getProgressBar
import ru.sulatskov.common.toast
import ru.sulatskov.main.MainActivity

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
        url = arguments?.getString(AppConst.ID_URL_KEY, "")
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.save_btn?.setOnClickListener {
            var result = downloadFile("$url.jpg")
            if (result != -1L) {
                toast(getString(R.string.save_success_text))
            } else {
                toast(getString(R.string.save_fail_text))
            }
        }

        view.photo_iv?.apply {
            val path = url + ".jpg"
            Picasso.with(view?.context)
                .load(path)
                .error(R.drawable.error)
                .placeholder(getProgressBar(context))
                .into(photo_iv)
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