package ru.sulatskov.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sulatskov.common.AppConst.API_URL
import java.util.concurrent.TimeUnit

class MainApiService {

    private val intercepter: Interceptor = Interceptor {
        try {
            val request = it.request()

            val newBuilder = request.newBuilder()

            newBuilder.header("Content-Type", "application/json; charset=utf-8")

            val proceed = it.proceed(newBuilder.build())

            proceed

        } catch (e: Exception) {
            it.proceed(it.request())
        }
    }

    private val api = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(intercepter)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun getAlbums(): Deferred<MutableList<Album>> = api.getAlbums()

    fun getPhotosByAlbumId(albumId: Int?): Deferred<MutableList<Photo>> =
        api.getPhotosByAlbumId(albumId)

}