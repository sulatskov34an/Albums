package ru.sulatskov.common

import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import ru.sulatskov.base.view.BaseFragment
import ru.sulatskov.main.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL


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
        try {
            val response = deferred.await()
            hideProgress()
            onComplete.invoke(response)
        }catch (e: Exception){
            toast("Ошибка интернет соединения")
        }
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

fun hasConnection(context: Context?): Boolean {
    var cm = context?.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var wifiInfo = cm.getNetworkInfo(android.net.ConnectivityManager.TYPE_WIFI)
    if (wifiInfo != null && wifiInfo.isConnected) {
        return true
    }
    wifiInfo = cm.getNetworkInfo(android.net.ConnectivityManager.TYPE_MOBILE)
    if (wifiInfo != null && wifiInfo.isConnected) {
        return true
    }
    wifiInfo = cm.activeNetworkInfo
    if (wifiInfo != null && wifiInfo.isConnected) {
        return true
    }
    return false
}

fun BaseFragment.updateToolbar(title: String, hasHomeUp: Boolean) {
    (activity as? MainActivity)?.updateToolbar(title, hasHomeUp)
}

fun BaseFragment.downloadFile(url: String?, relativePath: String = "/albums/"): Long? {
    val uri = android.net.Uri.parse(url)

    // Create request for android download manager
    activity?.apply {
        val downloadManager = getSystemService(android.app.Service.DOWNLOAD_SERVICE) as DownloadManager?
        val request = android.app.DownloadManager.Request(uri)

        //Setting title of request
        request.setTitle("Download " + uri.lastPathSegment)

        //Setting description of request
        request.setDescription("Download " + uri.path)

        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(
            this,
            android.os.Environment.DIRECTORY_DOWNLOADS + relativePath,
            uri.lastPathSegment
        )

        request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI or android.app.DownloadManager.Request.NETWORK_MOBILE)
        request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        //Enqueue download and save into referenceId
        return downloadManager?.enqueue(request)
    }

    return -1
}
