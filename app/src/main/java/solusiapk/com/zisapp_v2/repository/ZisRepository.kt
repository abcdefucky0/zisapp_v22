package solusiapk.com.zisapp_v2.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import solusiapk.com.zisapp_v2.api.ZisClient
import solusiapk.com.zisapp_v2.datamodel.ApiResponse
import solusiapk.com.zisapp_v2.datamodel.History_Campaign
import solusiapk.com.zisapp_v2.datamodel.Laporan_Zis
import solusiapk.com.zisapp_v2.datamodel.ZisCam_Report
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk_Respon

class ZisRepository {
    private val apiService = ZisClient.instance

    //untuk muncul semua
    suspend fun getZis(): List<ZisMasuk> {
        val response = apiService.getZis()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getZissaya(zis: String): List<ZisMasuk> {
        val response = apiService.getZissaya(zis)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        return emptyList()
    }


    suspend fun getDonasisaya(zis: String): List<ZisCam_Report> {
        val response = apiService.getDonasisaya(zis)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getHistoryCampaign(zis: String): List<History_Campaign> {
        val response = apiService.gethistorycampaign(zis)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getLaporanzis(dari: String, hingga: String): List<Laporan_Zis> {
        val response = apiService.getlaporanzis(dari, hingga)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getsaldozis(): Response<Laporan_Zis> {
        return apiService.getsaldozis()
//        val response = apiService.getsaldozis()
//        if (response.isSuccessful) {
//            return response.body() ?: emptyList()
//        }
//        return emptyList()
    }

    suspend fun getakumulasizisperiode(dari: String, hingga: String): Response<Laporan_Zis> {
       return apiService.getakumulasizisperiode(dari, hingga)
    }

    //untuk simpan
    suspend fun createZis(zis: ZisMasuk): ZisMasuk? {
        val response = apiService.createZis(zis)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun uploadbuktitransaksi(
        kodemuzakki: RequestBody,
        gambar: MultipartBody.Part
    ): ZisMasuk_Respon {
        // Memanggil fungsi API
        return try {
            val response = apiService.uploadbuktitransaksi(
                kodemuzakki,
                gambar,
            )
            if (response.isSuccessful) {
                response.body() ?: ZisMasuk_Respon("error", "Empty response body", null)
            } else {
                ZisMasuk_Respon("error", "Error Koneksi pada Server", null)
            }
        } catch (e: Exception) {
            ZisMasuk_Respon("error", e.message ?: "Unknown error", null)
        }
    }

}