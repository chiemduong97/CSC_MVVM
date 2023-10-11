package com.example.csc_mvvm.data.remote

import com.example.csc_mvvm.app.Preferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {
    fun newInstance(): Retrofit {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ${Preferences.newInstance().accessToken}")
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

        val logger: HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(30, TimeUnit.SECONDS)
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.203:8585/").client(okHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
