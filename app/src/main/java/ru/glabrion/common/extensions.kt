package ru.glabrion.common

import android.view.View
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import ru.glabrion.model.network.BaseResponse
import ru.glabrion.base.view.BaseFragment

interface ProgressManager {
    fun showProgress()
    fun hideProgress()
}

fun BaseFragment.hideProgress() {
    (activity as? ProgressManager)?.hideProgress()
}

fun BaseFragment.showProgress() {
    (activity as? ProgressManager)?.showProgress()
}

fun BaseFragment.toast(msg: String) {
    activity?.apply {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun <T, D> T.request(
    deferred: Deferred<BaseResponse<D>>,
    showProgress: Boolean = true,
    onComplete: (D) -> Unit
) where T : BaseFragment, T : CoroutineScope {
    if (showProgress) {
        showProgress()
    }
    launch(coroutineContext) {
        val response = deferred.await()
        hideProgress()
        if (response.id != null){
            onComplete.invoke(response.id)
        }
        if (response.result == false){
            toast("Неправильный логин или пароль")
        }
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}