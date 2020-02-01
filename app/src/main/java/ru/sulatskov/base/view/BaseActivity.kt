package ru.sulatskov.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseViewInterface {

    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        if (supportFragmentManager.backStackEntryCount == 0) {
            init(savedInstanceState)
        }
    }

    protected abstract fun init(state: Bundle?)
}