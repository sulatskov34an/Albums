package ru.glabrion

import android.app.Application
import org.koin.core.context.startKoin
import ru.glabrion.common.mainModule

open class AppApplication() : Application() {



    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule(applicationContext))
        }

    }
}