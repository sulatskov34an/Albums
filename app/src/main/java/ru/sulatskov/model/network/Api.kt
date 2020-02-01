package ru.sulatskov.model.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("albums")
    fun getAlbums(): Deferred<List<Album>>

    @GET("photos/{albumId}")
    fun getPhotosOfAlbum(
        @Path("albumId") albumId: Int
    ): Deferred<List<Photo>>
}