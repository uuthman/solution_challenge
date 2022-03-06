package com.example.solutionchallenge.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.example.solutionchallenge.MainCoroutineRule
import com.example.solutionchallenge.getOrAwaitValue
import com.example.solutionchallenge.repository.FakeUserRepositoryImpl

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class UserFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: UserFragmentViewModel

    @Before
    fun setup(){
        viewModel = UserFragmentViewModel(FakeUserRepositoryImpl())
    }

    @Test
    fun `check if user response is equals to null`(){
        viewModel.loadUsers("Test",9)
        assertThat(viewModel.userItemResponse).isEqualTo(null)
    }

    @Test
    fun `check if user page is equals to 1`(){
        viewModel.loadUsers("Test",9)
        assertThat(viewModel.userPage).isEqualTo(1)
    }

    @Test
    fun `check if user response is empty and return empty list state`(){
        viewModel.loadUsers("Test",9)
        assertThat(viewModel.uiState.getOrAwaitValue()).isEqualTo(UserUiState.EmptyList)
    }
}