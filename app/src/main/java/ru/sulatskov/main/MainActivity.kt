package ru.sulatskov.main

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import ru.sulatskov.R
import ru.sulatskov.base.view.BaseActivity
import ru.sulatskov.common.ProgressManager
import ru.sulatskov.common.gone
import ru.sulatskov.common.visible
import ru.sulatskov.main.screen.filters.FiltersFragment
import ru.sulatskov.main.screen.general.GeneralFragment
import ru.sulatskov.main.screen.ptotos.PhotosFragment

class MainActivity : BaseActivity(), ProgressManager {

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
        val generalFragment = GeneralFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.main_fragment_container,
            generalFragment
        ).addToBackStack(generalFragment.tag)
            .commit()
    }

    fun openPhotosScreen() {
        val photosFragment = PhotosFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                photosFragment
            )
            .addToBackStack(photosFragment.tag)
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