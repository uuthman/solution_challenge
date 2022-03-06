package com.example.solutionchallenge.di

import com.example.solutionchallenge.repository.UsersRepository
import com.example.solutionchallenge.repository.UsersRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UsersRepository> {
        UsersRepositoryImpl(get())
    }
}