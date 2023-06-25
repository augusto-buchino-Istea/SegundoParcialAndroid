package com.example.segundo_parcial

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET(value = "random")
    suspend fun randomJoke() : Response<Joke>

    @GET(value = "random")
    suspend fun getJokeByCategory(@Query("category") category: String) : Response<Joke>

    @GET(value = "categories")
    suspend fun getAllCategories() : Response<List<String>>

}