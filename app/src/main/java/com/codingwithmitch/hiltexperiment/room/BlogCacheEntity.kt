package com.codingwithmitch.hiltexperiment.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blogs")
data class BlogCacheEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var title: String,
    var body: String,
    var category: String,
    var image: String
)