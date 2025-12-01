package solusiapk.com.zisapp_v2.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MuzakkiClient {
    private const val BASE_URL = "https://zisapi.baktibersama.id/" // Ganti dengan base URL API Anda

    val instance: MuzakkiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MuzakkiService::class.java)
    }


    //tambahan untuk repository
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //tambahan untuk repository
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}