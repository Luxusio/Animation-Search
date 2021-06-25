package io.luxus.animation.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.luxus.animation.data.repository.DiscoverRepositoryImpl
import io.luxus.animation.domain.repository.DiscoverRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDiscoverRepository(discoverRepository: DiscoverRepositoryImpl): DiscoverRepository = discoverRepository


}