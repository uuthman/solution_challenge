package com.example.solutionchallenge.repository

import com.example.solutionchallenge.api.UserResponse
import com.example.solutionchallenge.api.UsersApi

class UsersRepositoryImpl(private val userApi : UsersApi) : UsersRepository {

    override suspend fun getUsers(query: String,perPage: Int,page: Int) : UserResponse = userApi.getUsers(query,perPage,page)
}