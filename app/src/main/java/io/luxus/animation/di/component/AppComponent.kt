package io.luxus.animation.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.luxus.animation.MainApplication
import io.luxus.animation.di.module.ApplicationModule
import io.luxus.animation.di.module.NetworkModule
import io.luxus.animation.di.module.RepositoryModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: MainApplication)

}