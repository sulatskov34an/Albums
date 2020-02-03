package ru.sulatskov.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sulatskov.common.AppConst.API_URL

class MainApiService {

        private val api = Retrofit.Builder()
        .baseUrl(API_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun getAlbums(): Deferred<MutableList<Album>> = api.getAlbums()

    fun getPhotosByAlbumId(albumId: Int?): Deferred<MutableList<Photo>> = api.getPhotosByAlbumId(albumId)

}