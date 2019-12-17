package ru.glabrion.model.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("user/{login}/{password}")
    fun     login(
        @Path("login") login: String,
        @Path("password") password: String
    ): Deferred<BaseResponse<String>>

}