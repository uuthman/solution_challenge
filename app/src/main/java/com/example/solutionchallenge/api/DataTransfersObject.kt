package com.example.solutionchallenge.api

import com.squareup.moshi.Json

data class Item(
    @Json(name = "login")
    val login: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
)