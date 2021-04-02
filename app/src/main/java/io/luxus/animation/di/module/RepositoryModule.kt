package io.luxus.animation.di.module

import dagger.Binds
import dagger.Module
import io.luxus.animation.data.repository.DiscoverRepositoryImpl
import io.luxus.animation.domain.repository.DiscoverRepository

@Module(includes = [NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideDiscoverRepository(discoverRepository: DiscoverRepositoryImpl): DiscoverRepository


}