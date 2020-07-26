package com.codingwithmitch.hiltexperiment.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogCacheEntity: BlogCacheEntity): Long

    @Insert
    suspend fun insertAll(blogCacheEntities: List<BlogCacheEntity>): Unit

    @Query("SELECT * FROM blogs")
    suspend fun get(): List<BlogCacheEntity>
}