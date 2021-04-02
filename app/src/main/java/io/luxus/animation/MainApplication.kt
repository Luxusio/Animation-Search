package io.luxus.animation

import android.app.Application
import android.content.res.Configuration
import io.luxus.animation.di.component.AppComponent
import io.luxus.animation.di.component.DaggerAppComponent
import javax.inject.Inject

class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var app: MainApplication
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        appComponent = DaggerAppComponent
            .builder().application(this).build()

        appComponent.inject(this)

    }

}