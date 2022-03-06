package com.example.solutionchallenge.di

import com.example.solutionchallenge.user.UserFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    viewModel {
        UserFragmentViewModel(get())
    }
}