package ru.sulatskov.main

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseActivity
import ru.sulatskov.common.*
import ru.sulatskov.main.screen.filters.FiltersFragment
import ru.sulatskov.main.screen.general.GeneralFragment
import ru.sulatskov.main.screen.photo.PhotoFragment
import ru.sulatskov.main.screen.ptotos.PhotosFragment

class MainActivity : BaseActivity(), ProgressManager {

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun init(state: Bundle?) {
        openGeneralScreen()
        if (hasConnection(this)) {
            no_internet_ll.gone()
        } else {
            no_internet_ll.visible()
        }
    }

    override fun showProgress() {
        main_progress.visible()
    }

    override fun hideProgress() {
        main_progress.gone()
    }

    fun openGeneralScreen() {
        val generalFragment = GeneralFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.main_fragment_container,
            generalFragment
        ).addToBackStack(generalFragment.tag)
            .commit()
    }

    fun openPhotosScreen(albumId: Int?) {
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
        val filtersFragment = FiltersFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                filtersFragment
            )
            .addToBackStack(filtersFragment.tag)
            .commit()
    }

}