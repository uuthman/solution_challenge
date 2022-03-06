package com.example.solutionchallenge.repository

import com.example.solutionchallenge.api.Item
import com.example.solutionchallenge.api.UserResponse

class FakeUserRepositoryImpl : UsersRepository {

    override suspend fun getUsers(query: String, perPage: Int, page: Int): UserResponse {
      return UserResponse(mutableListOf())
    }
}