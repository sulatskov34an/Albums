package ru.glabrion.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.glabrion.common.AppConst.Companion.API_URL

class MainApiService {

    private val intercepter: Interceptor = Interceptor {
        try {
            val request = it.request()
            var newBuilder = request.newBuilder()
            newBuilder.header("Content-Type", "application/x-www-form-urlencoded")

            var proceed = it.proceed(newBuilder.build())

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
                .build()
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun login(login: String, password: String): Deferred<BaseResponse<String>> = api.login(login, password)

}