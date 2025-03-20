package com.example.tpgdemo.data

import com.example.tpgdemo.BuildConfig
import com.example.tpgdemo.R
import com.example.tpgdemo.network.CatsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

interface AppContainer{
    val catsPhotosRepository: CatsPhotosRepository
}
private const val BASE_URL ="https://api.thecatapi.com/v1/"

private const val API_KEY = BuildConfig.API_KEY

/**
 * Default App Container that provides instance of [CatsPhotosRepository]
 */
class DefaultAppContainer : AppContainer {
    private val json = Json {
        ignoreUnknownKeys = true // Prevent errors if API returns extra fields
    }

    // Interceptor to add the API key in headers
    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest: Request = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("x-api-key", API_KEY) // Add API key in the header
            .build()
        chain.proceed(newRequest)
    }

    // OkHttpClient with the Interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()


   private val retrofitService : CatsApiService by lazy {
        retrofit.create(CatsApiService::class.java)

    }

    override val catsPhotosRepository: CatsPhotosRepository by lazy {
        NetworkCatsPhotosRepository(retrofitService)
    }
}