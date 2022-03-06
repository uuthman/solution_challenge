package com.example.solutionchallenge.extensions

import com.example.solutionchallenge.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.handleThrowable(): Pair<Int, Int>{
    val errorPair: Pair<Int,Int> = when{
        this is UnknownHostException -> Pair(R.string.network_problem,R.string.check_internet)
        this is HttpException && this.code() in 500..599 -> Pair(R.string.something_went_wrong, R.string.sorry_incov)
        this is HttpException -> Pair(R.string.try_again,R.string.unkown_error)
        this is HttpException && this.code() == 422 -> Pair(R.string.something_went_wrong, R.string.validation_failed)
        this is SocketTimeoutException -> Pair(R.string.network_problem,R.string.check_internet)
        else -> Pair(R.string.try_again,R.string.unkown_error)
    }
    return errorPair
}