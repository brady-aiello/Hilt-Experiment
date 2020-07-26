package com.codingwithmitch.hiltexperiment.di

import com.codingwithmitch.hiltexperiment.repository.MainRepository
import com.codingwithmitch.hiltexperiment.retrofit.BlogRetrofit
import com.codingwithmitch.hiltexperiment.retrofit.NetworkMapper
import com.codingwithmitch.hiltexperiment.room.BlogDao
import com.codingwithmitch.hiltexperiment.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper): MainRepository {
        return MainRepository(blogDao, blogRetrofit, cacheMapper, networkMapper)
    }
}