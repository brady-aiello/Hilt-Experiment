package com.codingwithmitch.hiltexperiment.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogNetworkEntity (
    @SerializedName("pk")
    @Expose
    val id: Int,
    @Expose
    val title: String,
    @Expose
    val body: String,
    @Expose
    val image: String,
    @Expose
    val category: String
)