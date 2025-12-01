package solusiapk.com.zisapp_v2.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import solusiapk.com.zisapp_v2.datamodel.ApiResponse
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.datamodel.Usulan

interface CampaignService2 {
    @GET("Usulan/usulanlist")
    suspend fun getUsulans(): retrofit2.Response<List<Usulan>> // Gunakan suspend untuk Coroutines

    @GET("Usulan/cariusulan")
    suspend fun getusulanone(
        @Query("id") id: Int?,
    ): Response<Usulan>

//    @POST("Usulan/create")
//    suspend fun createUsulan(@Body post: Usulan): Response<ApiResponse>

    @Multipart
    @POST("Usulan/create")
    suspend fun createUsulan(
        @Part("kodemuzakki") kodemuzakki: RequestBody,
        @Part("namapenerima") namapenerima: RequestBody,
        @Part("jenisbantuan") jenisbantuan: RequestBody,
        @Part("besarbantuan") besarbantuan: RequestBody,
        @Part("deskripsisingkat") deskripsisingkat: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part gambar: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part,
        @Part gambar3: MultipartBody.Part
    ): retrofit2.Response<ApiResponse>
}