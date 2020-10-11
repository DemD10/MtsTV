package com.app.data

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun api(sharedPreferences: SharedPreferences): ApiComponent = ApiModule(sharedPreferences)

internal class ApiModule(sharedPreferences: SharedPreferences): ApiComponent {

    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor).build()

    private val moshi = Moshi.Builder().build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    override val filmsApi: FilmsApi = retrofit.create(FilmsApi::class.java)

    override val appCache: AppCache = AppCache(sharedPreferences, moshi)
}