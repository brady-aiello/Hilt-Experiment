package com.codingwithmitch.hiltexperiment.repository

import com.codingwithmitch.hiltexperiment.model.Blog
import com.codingwithmitch.hiltexperiment.retrofit.BlogRetrofit
import com.codingwithmitch.hiltexperiment.retrofit.NetworkMapper
import com.codingwithmitch.hiltexperiment.room.BlogDao
import com.codingwithmitch.hiltexperiment.room.CacheMapper
import com.codingwithmitch.hiltexperiment.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository constructor(private val blogDao: BlogDao,
                                 private val blogRetrofit: BlogRetrofit,
                                 private val cacheMapper: CacheMapper,
                                 private val networkMapper: NetworkMapper) {

    suspend fun getBlogs(): Flow<DataState<List<Blog>>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                val networkBlogs = blogRetrofit.get()
                val blogs = networkMapper.mapFromEntityList(networkBlogs)
                val blogCacheEntities = cacheMapper.mapToEntityList(blogs)
                blogDao.insertAll(blogCacheEntities)
                val cachedBlogs = blogDao.get()
                emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
}