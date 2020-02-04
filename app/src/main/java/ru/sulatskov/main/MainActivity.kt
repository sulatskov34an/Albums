package ru.sulatskov.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseActivity
import ru.sulatskov.common.*
import ru.sulatskov.main.screen.filters.FiltersFragment
import ru.sulatskov.main.screen.general.GeneralFragment
import ru.sulatskov.main.screen.photo.PhotoFragment
import ru.sulatskov.main.screen.ptotos.PhotosFragment
import ru.sulatskov.model.prefs.PrefsService

class MainActivity : BaseActivity(), ProgressManager {

    private val prefsService: PrefsService by inject()
    val connection: ConnectionProvider by inject()

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun init(state: Bundle?) {
        openGeneralScreen()
    }

    override fun showProgress() {
        main_progress.visible()
    }

    override fun hideProgress() {
        main_progress.gone()
    }

    fun openGeneralScreen() {
        checkConnection()
        val generalFragment = GeneralFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.main_fragment_container,
            generalFragment
        ).addToBackStack(generalFragment.tag)
            .commit()
    }

    fun openPhotosScreen(albumId: Int?) {
        checkConnection()
        val photosFragment = PhotosFragment()
        val bundle = Bundle()
        bundle.putInt(AppConst.ID_ALBUM_KEY, albumId ?: 0)
        photosFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                photosFragment
            )
            .addToBackStack(photosFragment.tag)
            .commit()
    }

    fun openPhotoScreen(url: String?) {
        checkConnection()
        val photoFragment = PhotoFragment()
        val bundle = Bundle()
        bundle.putString(AppConst.ID_URL_KEY, url)
        photoFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                photoFragment
            )
            .addToBackStack(photoFragment.tag)
            .commit()
    }

    fun openFiltersScreen() {
        checkConnection()
        val filtersFragment = FiltersFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                filtersFragment
            )
            .addToBackStack(filtersFragment.tag)
            .commit()
    }

    fun checkConnection(){
        if (connection.isConnected()) {
            no_internet_ll.gone()
        } else {
            no_internet_ll.visible()
        }
    }
}