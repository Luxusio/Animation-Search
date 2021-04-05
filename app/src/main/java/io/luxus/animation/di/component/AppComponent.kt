package io.luxus.animation.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.luxus.animation.MainApplication
import io.luxus.animation.di.module.ApplicationModule
import io.luxus.animation.di.module.NetworkModule
import io.luxus.animation.di.module.RepositoryModule
import io.luxus.animation.presentation.view.fragment.AnimationListFragment
import io.luxus.animation.presentation.viewmodel.AnimationListViewModel
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
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

    // application
    fun inject(mainApplication: MainApplication)

    // fragment
    fun inject(fragment: AnimationListFragment)

    //// viewModel
    fun inject(animationListViewModel: AnimationListViewModel)



}
