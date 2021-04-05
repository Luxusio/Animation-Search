package io.luxus.animation.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.luxus.animation.data.repository.DiscoverRepositoryImpl
import io.luxus.animation.domain.repository.DiscoverRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDiscoverRepository(discoverRepository: DiscoverRepositoryImpl): DiscoverRepository = discoverRepository


}