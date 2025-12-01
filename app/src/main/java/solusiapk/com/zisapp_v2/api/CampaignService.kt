package solusiapk.com.zisapp_v2.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import solusiapk.com.zisapp_v2.datamodel.ApiResponse
import solusiapk.com.zisapp_v2.datamodel.Campaign

interface CampaignService {
    @GET("ApiCampain/campainlist")
    suspend fun getCampaigns(): retrofit2.Response<List<Campaign>> // Gunakan suspend untuk Coroutines

    @GET("ApiCampain/caricampaign")
    suspend fun getcampainone(
        @Query("id") id: Int?,
    ): Response<Campaign>

    @POST("ApiCampain/createcampaign")
    suspend fun createCampaign(@Body post: Campaign): Response<ApiResponse>

//    suspend fun createMuzakki(@Body post: Muzakki): retrofit2.Response<Muzakki>



//    // READ (Ambil semua produk)
//    @GET("api/products")
//    suspend fun getProducts(): Response<List<Product>>
//
//    // READ (Ambil satu produk)
//    @GET("api/products/{id}")
//    suspend fun getProduct(@Path("id") id: Int): Response<Product>
//
//    // CREATE
//    @POST("api/products")
//    suspend fun createProduct(@Body product: Product): Response<ApiResponse>
//
//    // UPDATE
//    @PUT("api/products/{id}")
//    suspend fun updateProduct(@Path("id") id: Int, @Body product: Product): Response<ApiResponse>
//
//    // DELETE
//    @DELETE("api/products/{id}")
//    suspend fun deleteProduct(@Path("id") id: Int): Response<ApiResponse>

}