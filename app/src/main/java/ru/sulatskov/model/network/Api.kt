package ru.sulatskov.model.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("albums")
    fun getAlbums(): Deferred<List<Album>>

    @GET("photos")
    fun getPhotosByAlbumId(
        @Query("albumId") albumId: Int
    ): Deferred<List<Photo>>
}