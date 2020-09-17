package ru.sulatskov.common

import android.app.DownloadManager
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import ru.sulatskov.base.view.BaseFragment
import ru.sulatskov.main.MainActivity

interface ProgressManager {
    fun showProgress()
    fun hideProgress()
}

fun BaseFragment.toast(msg: String) {
    activity?.apply {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

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

fun BaseFragment.updateToolbar(title: String, hasHomeUp: Boolean) {
    (activity as? MainActivity)?.updateToolbar(title, hasHomeUp)
}

fun BaseFragment.downloadFile(url: String?, relativePath: String = "/albums/"): Long? {
    val uri = android.net.Uri.parse(url)

    activity?.apply {
        val downloadManager =
            getSystemService(android.app.Service.DOWNLOAD_SERVICE) as DownloadManager?
        val request = DownloadManager.Request(uri)
        request.setTitle(uri.lastPathSegment)
        request.setDescription(uri.path)
        request.setDestinationInExternalFilesDir(
            this,
            android.os.Environment.DIRECTORY_DOWNLOADS + relativePath,
            uri.lastPathSegment
        )
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        return downloadManager?.enqueue(request)
    }
    return -1
}
