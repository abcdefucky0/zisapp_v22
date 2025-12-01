package solusiapk.com.zisapp_v2.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import solusiapk.com.zisapp_v2.datamodel.Muzakki
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk

interface MuzakkiService {
    @GET("ApiMuzakki") // Ganti "products" dengan path endpoint API Anda
    suspend fun getMuzakki(): retrofit2.Response<List<Muzakki>> // Gunakan suspend untuk Coroutines

    @POST("ApiMuzakki/createmuzakki")
    suspend fun createMuzakki(@Body post: Muzakki): retrofit2.Response<Muzakki>
//    suspend fun createPost(@Body post: Post): Response<Post>
//    suspend fun createProduct(@Body product: Product): Product // Mengembalikan objek yang

    @GET("ApiMuzakki/cariMuzakki/{id}")
    suspend fun getmuzakkione(@Path("id") id: String): Response<Muzakki>

    //jika carian banyak beda pada respon
//    @GET("Muzakki/riwayatsetoransaya/{id}")
//    suspend fun getZissaya(@Path("id") id: String): Response<List<ZisMasuk>>
//    @PUT("ApiZis/updateZis/{id}")
//    suspend fun updateZis(@Path("id") id: String, @Body post: ZisMasuk): Response<ZisMasuk>
//
//    @DELETE("ApiZis/deleteZis/{id}")
//    suspend fun deletePost(@Path("id") id: String): Response<Unit>
}