package solusiapk.com.zisapp_v2.viewmodel

import android.R.attr.level
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import solusiapk.com.zisapp_v2.activity.MainActivity
import solusiapk.com.zisapp_v2.datamodel.ApiResponse
import solusiapk.com.zisapp_v2.repository.CampaignRepository2
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.datamodel.Usulan
import solusiapk.com.zisapp_v2.until.Result
import kotlin.jvm.java

class CampaignViewModel2(private val repository: CampaignRepository2) : ViewModel() {

    private val _products = MutableStateFlow<List<Usulan>>(emptyList())
    val products: StateFlow<List<Usulan>> = _products

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _usulanone = MutableLiveData<Usulan?>()
    val usulanone: LiveData<Usulan?> = _usulanone

    private val _apiResponse = MutableLiveData<ApiResponse>()
    val apiResponse: LiveData<ApiResponse> = _apiResponse

//    init {
//        fetchProducts()
//    }

//    fun fetchProducts() {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            when (val result = repository.getProducts()) {
//                is Result.Success -> {
//                    _products.value = result.data
//                    _uiState.value = UiState.Idle
//                }
//                is Result.Failure -> {
//                    _uiState.value = UiState.Error(result.exception.message ?: "Failed to fetch products")
//                }
//            }
//        }
//    }

//    fun createCampaign(product: Usulan) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            when (val result = repository.createUsulan(product)) {
//                is Result.Success -> {
//                    //fetchProducts() // Refresh list
//                    _uiState.value = UiState.Success(result.data.message)
//                }
//
//                is Result.Failure -> {
//                    _uiState.value =
//                        UiState.Error(result.exception.message ?: "Failed to create product")
//                }
//            }
//        }
//    }


    fun createCampaign(
        kodemuzakki: RequestBody,
        namapemerima: RequestBody,
        jenisbantuan: RequestBody,
        besarbantuan: RequestBody,
        deskripsisingkat: RequestBody,
        alamat: RequestBody,
        foto1: MultipartBody.Part,
        foto2: MultipartBody.Part,
        foto3: MultipartBody.Part
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _usulanone.value = null

            try {
                val response = repository.createUsulan(
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
                if (response != null) {
                    _apiResponse.value = response
                    _isLoading.value = false
                } else {
                    _error.value = "gagal menyimpan usulan"
                }

            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

//    fun updateProduct(id: Int, product: Product) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            when (val result = repository.updateProduct(id, product)) {
//                is Result.Success -> {
//                    fetchProducts()
//                    _uiState.value = UiState.Success(result.data.message)
//                }
//                is Result.Failure -> {
//                    _uiState.value = UiState.Error(result.exception.message ?: "Failed to update product")
//                }
//            }
//        }
//    }

    //    fun deleteProduct(id: Int) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            when (val result = repository.deleteProduct(id)) {
//                is Result.Success -> {
//                    fetchProducts()
//                    _uiState.value = UiState.Success(result.data.message)
//                }
//                is Result.Failure -> {
//                    _uiState.value = UiState.Error(result.exception.message ?: "Failed to delete product")
//                }
//            }
//        }
//    }
    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val message: String) : UiState()
        data class Error(val message: String) : UiState()
    }
}


