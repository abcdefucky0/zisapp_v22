package solusiapk.com.zisapp_v2.repository

import retrofit2.Response
import solusiapk.com.zisapp_v2.api.CampaignClient
import solusiapk.com.zisapp_v2.datamodel.Campaign

class CampaignRepository {
    private val apiService = CampaignClient.instance

    suspend fun getCampaign(): List<Campaign> {
        val response = apiService.getCampaigns()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        return emptyList()
    }

    suspend fun getcampainone(id: Int): Response<Campaign> {
        return apiService.getcampainone(id)
    }
}