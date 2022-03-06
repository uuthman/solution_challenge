package com.example.solutionchallenge.di

import com.example.solutionchallenge.api.UsersApi
import com.example.solutionchallenge.util.Environment
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val apiModule = module {
    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(Environment.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(client)
            .build()
    }

    single {
        get<Retrofit>().create(UsersApi::class.java)
    }

    single{
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }


}