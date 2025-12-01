package solusiapk.com.zisapp_v2.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import solusiapk.com.zisapp_v2.datamodel.ApiResponse
import solusiapk.com.zisapp_v2.datamodel.History_Campaign
import solusiapk.com.zisapp_v2.datamodel.Laporan_Zis
import solusiapk.com.zisapp_v2.datamodel.Muzakki
import solusiapk.com.zisapp_v2.datamodel.ZisCam_Report
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk_Respon

interface ZisService {
    @GET("ApiZis") // Ganti "products" dengan path endpoint API Anda
    suspend fun getZis(): retrofit2.Response<List<ZisMasuk>> // Gunakan suspend untuk Coroutines

    @GET("ApiZis/riwayatsetoransaya/{id}")
    suspend fun getZissaya(@Path("id") id: String): Response<List<ZisMasuk>>

//    @GET("ApiZis/riwayatdonasisaya/{id}")
//    suspend fun getDonasisaya(@Path("id") id: String): Response<List<ZisMasuk>>

    @GET("ApiZis/riwayatdonasisaya/{id}")
    suspend fun getDonasisaya(@Path("id") id: String): Response<List<ZisCam_Report>>

    @GET("ApiZis/historykode/{id}")
    suspend fun gethistorycampaign(@Path("id") id: String): Response<List<History_Campaign>>

    @GET("ApiZis/laporanzis")
    suspend fun getlaporanzis(
        @Query("dari") dari: String?,
        @Query("hingga") hingga: String?
    ): Response<List<Laporan_Zis>>

//    @GET("ApiZis/carisaldo")
//    suspend fun getsaldozis(): Response<List<Laporan_Zis>>

    @GET("ApiZis/carisaldo")
    suspend fun getsaldozis(): Response<Laporan_Zis>

    @GET("ApiZis/sirkulasiperiode")
    suspend fun getakumulasizisperiode(
        @Query("dari") dari: String?,
        @Query("hingga") hingga: String?
    ): Response<Laporan_Zis>

//    @GET("ApiZis/sirkulasiperiode")
//    suspend fun getakumulasizisperiode(
//        @Query("dari") dari: String?,
//        @Query("hingga") hingga: String?
//    ): Response<List<Laporan_Zis>>


    @POST("ApiZis/buatsetoran")
    suspend fun createZis(@Body post: ZisMasuk): retrofit2.Response<ZisMasuk>

//    suspend fun createPost(@Body post: Post): Response<Post>
//    suspend fun createProduct(@Body product: Product): Product // Mengembalikan objek

    @Multipart
    @POST("ApiZis/uploadbuki")
    suspend fun uploadbuktitransaksi(
        @Part("kodemuzakki") kodemuzakki: RequestBody,
        @Part gambar: MultipartBody.Part,
    ): retrofit2.Response<ZisMasuk_Respon>
}
