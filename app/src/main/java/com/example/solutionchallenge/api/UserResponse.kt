package com.example.solutionchallenge.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserResponse (
    @Json(name = "items")
    val items: MutableList<Item>
)