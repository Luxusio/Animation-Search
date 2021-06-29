package io.luxus.animation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {


    //lateinit var appComponent: AppComponent

    //companion object {
    //    lateinit var app: MainApplication
    //}

    override fun onCreate() {
        super.onCreate()

        //app = this
        //appComponent = DaggerAppComponent
        //    .builder().application(this).build()

        //appComponent.inject(this)

    }

}