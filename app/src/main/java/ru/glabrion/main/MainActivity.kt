package ru.glabrion.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.glabrion.R
import ru.glabrion.base.view.BaseActivity
import ru.glabrion.common.ProgressManager
import ru.glabrion.common.gone
import ru.glabrion.common.visible
import ru.glabrion.main.screen.general.GeneralFragment

class MainActivity : BaseActivity(), ProgressManager {

    private val generalFragment = GeneralFragment()
    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun init(state: Bundle?) {
        supportFragmentManager.beginTransaction().add(
            R.id.main_fragment_container,
            generalFragment
        ).commit()
    }

    override fun showProgress() {
        main_progress.visible()
    }

    override fun hideProgress() {
        main_progress.gone()
    }

}