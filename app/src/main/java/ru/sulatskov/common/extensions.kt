package ru.sulatskov.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.sulatskov.base.view.BaseFragment

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
    deferred: Deferred<D>,
    showProgress: Boolean = true,
    onComplete: (D) -> Unit
) where T : BaseFragment, T : CoroutineScope {
    if (showProgress) {
        showProgress()
    }
    launch(coroutineContext) {
        val response = deferred.await()
        hideProgress()
            onComplete.invoke(response)
    }
}

fun ViewGroup.inflate(@LayoutRes layout: Int): View =
    LayoutInflater.from(context).inflate(layout, this, false)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun getProgressBar(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    return circularProgressDrawable
}