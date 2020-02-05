package ru.sulatskov.base.view

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

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