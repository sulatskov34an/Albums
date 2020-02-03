package ru.sulatskov

import android.app.Application
import org.koin.core.context.startKoin
import ru.sulatskov.common.mainModule

open class AppApplication() : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule(applicationContext))
        }
    }
}