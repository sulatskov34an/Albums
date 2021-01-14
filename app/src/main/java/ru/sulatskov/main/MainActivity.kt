package ru.sulatskov.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseActivity
import ru.sulatskov.common.*
import ru.sulatskov.main.screen.filters.FiltersFragment
import ru.sulatskov.main.screen.general.GeneralFragment
import ru.sulatskov.main.screen.slider.SliderFragment
import ru.sulatskov.main.screen.album.AlbumFragment

class MainActivity : BaseActivity(), ProgressManager {

    private val connection: ConnectionProvider by inject()

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun init(state: Bundle?) {
        if (supportFragmentManager.backStackEntryCount == 0){
            openGeneralScreen(AppConst.SORT_DEFAULT)
        }
    }

    fun openGeneralScreen(sortBy: String?) {
        checkConnection()
        val generalFragment = GeneralFragment()
        val bundle = Bundle()
        bundle.putString(AppConst.SORT_KEY, sortBy ?: AppConst.SORT_DEFAULT)
        generalFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(
            R.id.main_fragment_container,
            generalFragment
        ).addToBackStack(generalFragment.tag)
            .commit()
    }

    fun openAlbumScreen(albumId: Int?) {
        checkConnection()
        val albumFragment = AlbumFragment()
        val bundle = Bundle()
        bundle.putInt(AppConst.ID_ALBUM_KEY, albumId ?: 0)
        albumFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                albumFragment
            )
            .addToBackStack(albumFragment.tag)
            .commit()
    }

    fun openSliderScreen(albumId: Int?, photoCount: Int?) {
        checkConnection()
        val sliderFragment = SliderFragment()
        val bundle = Bundle()
        bundle.putInt(AppConst.ID_ALBUM_KEY, albumId ?: 0)
        bundle.putInt(AppConst.PHOTOS_COUNT_KEY, photoCount ?: 0)
        sliderFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                sliderFragment
            )
            .addToBackStack(sliderFragment.tag)
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

    private fun checkConnection() {
        if (connection.isConnected()) {
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
}