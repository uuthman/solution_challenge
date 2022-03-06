package com.example.solutionchallenge.api

import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("/search/users")
    suspend fun getUsers(
        @Query("q") searchQuery: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int) : UserResponse
}