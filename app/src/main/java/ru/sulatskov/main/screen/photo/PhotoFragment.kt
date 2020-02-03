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

        view?.save_btn?.setOnClickListener {
            var result = downloadFile("$url.jpg")
            if (result != -1L) {
                toast("Сохранение фотографии прошло успешно")
            } else {
                toast("Фото не удалось сохранить")
            }
        }

        view?.photo_iv?.apply {
            val path = url + ".jpg"
            Picasso.with(view?.context)
                .load(path)
                .error(R.drawable.error)
                .placeholder(getProgressBar(context))
                .into(photo_iv)
        }
    }
}