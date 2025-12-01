package solusiapk.com.zisapp_v2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.datamodel.Muzakki
import solusiapk.com.zisapp_v2.repository.CampaignRepository
import java.time.LocalDate

class CampaignViewModel : ViewModel() {
    private val _campaigns = MutableLiveData<List<Campaign>>()
    val campaings: LiveData<List<Campaign>> = _campaigns

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    //disini dipanggil repository
    private val repository = CampaignRepository()

    // private val _posts = MutableLiveData<List<Post>>()
    // val posts: LiveData<List<Post>> = _posts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    //tampil hanya 1 baris
    private val _campaignone = MutableLiveData<Campaign?>()
    val campaignone: LiveData<Campaign?> = _campaignone


    private val _campaigncreated = MutableLiveData<Campaign?>()
    val campaignCreated: LiveData<Campaign?> = _campaigncreated

    fun fetchCampaignsRepo() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getCampaign()
                _campaigns.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }

    }

    fun fetcampaignone(id: Int) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = repository.getcampainone(id)
                if (response.isSuccessful) {
                    _campaignone.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                    _campaignone.value = null // Atur ke null jika gagal
                }
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.localizedMessage}"
                _campaignone.value = null // Atur ke null jika ada exception
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createCampaign(
        jenismuzakki: String,
        namamuzakki: String,
        jk: String,
        telepon: String,
        alamat: String,
        email: String
    ) {
        _isLoading.value = true
        _error.value = null
        _campaigncreated.value = null

        // Untuk contoh ini, kita tetapkan userId = 1
        val tanggalnya = LocalDate.parse("2025-06-01")
        val newPost = Campaign(
            id = 0,
            slug = "",
            title = "",
            description = "",
            donasiterkumpul = 0,
            start = "",
            end = "",
            target = 0,
            status = "",
            image = "",
            category_id = 0,
            user_id = 0,
            create_at = "",
            update_at = "",
            agency_id = 0,
            delete_at = ""
        )


    }
}