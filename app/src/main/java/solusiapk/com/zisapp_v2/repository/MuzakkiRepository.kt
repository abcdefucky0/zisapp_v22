package solusiapk.com.zisapp_v2.repository

import retrofit2.Response
import solusiapk.com.zisapp_v2.api.MuzakkiClient
import solusiapk.com.zisapp_v2.datamodel.Muzakki
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk

class MuzakkiRepository {
    private val apiService = MuzakkiClient.instance

    suspend fun getMuzakkiOne(email: String): Response<Muzakki> {
        return apiService.getmuzakkione(email)
    }

    suspend fun createMuzakki(muzakki: Muzakki): Muzakki? {
        val response = apiService.createMuzakki(muzakki)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

//    suspend fun getMuzakkiOne(muzakki: String): List<Muzakki> {
//        val response = apiService.getmuzakkione(muzakki)
//        if (response.isSuccessful) {
//            return response.body() ?: emptyList()
//        }
//        return emptyList()
//    }
}