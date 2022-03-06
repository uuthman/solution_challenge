package com.example.solutionchallenge.repository

import com.example.solutionchallenge.api.UserResponse

interface UsersRepository {
    suspend fun getUsers(query: String,perPage: Int,page: Int) : UserResponse
}