package ru.sulatskov.model.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sulatskov.common.AppConst.API_URL
import java.util.concurrent.TimeUnit

class MainApiService {

    private val interceptor: Interceptor = Interceptor {
        try {
            val request = it.request()

            val newBuilder = request.newBuilder()

            newBuilder.header("Content-Type", "application/json; charset=utf-8")

            val proceed = it.proceed(newBuilder.build())

            proceed

        } catch (e: Exception) {
            Log.d("Exception ${javaClass.simpleName}", e.toString())
            it.proceed(it.request())
        }
    }

    private val api = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    suspend fun getAlbums(): MutableList<Album> = api.getAlbums().await()

    suspend fun getPhotosByAlbumId(albumId: Int?): MutableList<Photo> =
        api.getPhotosByAlbumId(albumId).await()

}