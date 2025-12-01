package solusiapk.com.zisapp_v2.repository

import android.R.attr.level
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import solusiapk.com.zisapp_v2.api.CampaignService
import solusiapk.com.zisapp_v2.api.CampaignService2
import solusiapk.com.zisapp_v2.datamodel.ApiResponse
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.datamodel.Usulan
import solusiapk.com.zisapp_v2.until.Result

class CampaignRepository2(private val apiService: CampaignService2) {

//    suspend fun getProducts(): Result<List<Campaign>> {
//        return try {
//            val response = apiService.getProducts()
//            if (response.isSuccessful) {
//                Result.success(response.body() ?: emptyList())
//            } else {
//                Result.failure(HttpException(response))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

//    suspend fun createUsulan(product: Usulan): Result<ApiResponse> {
//        return try {
//            val response = apiService.createUsulan(product)
//            if (response.isSuccessful) {
//                // Return Success dengan data yang valid
//                Result.Success(response.body()!!)
//            } else {
//                // Return Failure jika respons tidak berhasil
//                Result.Failure(Exception("API Error: ${response.code()}"))
//            }
//        } catch (e: Exception) {
//            // Return Failure jika ada exception
//            Result.Failure(e)
//        }
//    }


    suspend fun createUsulan(
        kodemuzakki: RequestBody,
        namapemerima: RequestBody,
        jenisbantuan: RequestBody,
        besarbantuan: RequestBody,
        deskripsisingkat: RequestBody,
        alamat: RequestBody,
        foto1: MultipartBody.Part,
        foto2: MultipartBody.Part,
        foto3: MultipartBody.Part
    ): ApiResponse {
        // Memanggil fungsi API
        return try {
            val response =  apiService.createUsulan(
                kodemuzakki,
                namapemerima,
                jenisbantuan,
                besarbantuan,
                deskripsisingkat,
                alamat,
                foto1,
                foto2,
                foto3
            )

            if (response.isSuccessful) {
                response.body() ?: ApiResponse("error", "Empty response body")
            } else {
                ApiResponse("error", "Error Koneksi pada Server")
            }
        } catch (e: Exception) {
            ApiResponse("error", e.message ?: "Unknown error")
        }
    }
//    suspend fun createCampaign(product: Campaign): Result<ApiResponse> {
//        return try {
//            val response = apiService.createCampaign(product)
//            if (response.isSuccessful) {
//                Result.success(response.body() ?: ApiResponse("error", "Empty response"))
//            } else {
//                Result.failure(HttpException(response))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

//    suspend fun updateProduct(id: Int, product: Product): Result<ApiResponse> {
//        return try {
//            val response = apiService.updateProduct(id, product)
//            if (response.isSuccessful) {
//                Result.success(response.body() ?: ApiResponse("error", "Empty response"))
//            } else {
//                Result.failure(HttpException(response))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

//    suspend fun deleteProduct(id: Int): Result<ApiResponse> {
//        return try {
//            val response = apiService.deleteProduct(id)
//            if (response.isSuccessful) {
//                Result.success(response.body() ?: ApiResponse("error", "Empty response"))
//            } else {
//                Result.failure(HttpException(response))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
}