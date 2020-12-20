package ru.sulatskov.base.view

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import ru.sulatskov.main.MainActivity
import ru.sulatskov.model.network.MainApiService
import ru.sulatskov.model.prefs.PrefsService
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope, BaseViewInterface {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }
}