package com.my.hiltapplication.network

import com.my.hiltapplication.BuildConfig
import com.my.hiltapplication.noupdate.header.HeaderInterceptor
import com.my.hiltapplication.noupdate.variant.Name
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Network @Inject constructor(
    private var headerInterceptor : HeaderInterceptor
){
    private val callTimeOut = 10L
    private val connectTimeOut = 10L

    private fun getLogInterceptor() : HttpLoggingInterceptor {
        val logger = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return HttpLoggingInterceptor().setLevel(logger)
    }

    private fun getHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLogInterceptor())
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
            .callTimeout(callTimeOut, TimeUnit.SECONDS)
            .addNetworkInterceptor(headerInterceptor)
            .build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Name.url).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(this.getHttpClient()).build()
    }
}