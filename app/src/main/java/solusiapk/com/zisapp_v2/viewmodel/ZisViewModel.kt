package solusiapk.com.zisapp_v2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import solusiapk.com.zisapp_v2.datamodel.ApiResponse
import solusiapk.com.zisapp_v2.datamodel.History_Campaign
import solusiapk.com.zisapp_v2.datamodel.Laporan_Zis
import solusiapk.com.zisapp_v2.datamodel.Muzakki
import solusiapk.com.zisapp_v2.datamodel.ZisCam_Report
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk_Respon
import solusiapk.com.zisapp_v2.repository.ZisRepository
import java.time.LocalDate
import kotlin.collections.toMutableList

class ZisViewModel : ViewModel() {
    //disini dipanggil repository
    private val repository = ZisRepository()

    //untuk pencarian dan memasukkan kedalam datalist
    private val _zis = MutableLiveData<List<ZisMasuk>>()
    val zis: LiveData<List<ZisMasuk>> = _zis

    private val _ziscam = MutableLiveData<List<ZisCam_Report>>()
    val ziscam: LiveData<List<ZisCam_Report>> = _ziscam

    private val _hiscam = MutableLiveData<List<History_Campaign>>()
    val hiscam: LiveData<List<History_Campaign>> = _hiscam

    private val _lapzis = MutableLiveData<List<Laporan_Zis>>()
    val lapzis: LiveData<List<Laporan_Zis>> = _lapzis

    private val _saldo = MutableLiveData<Laporan_Zis?>()
    val saldo: LiveData<Laporan_Zis?> = _saldo

    private val _saldoper = MutableLiveData<Laporan_Zis?>()
    val saldoper: LiveData<Laporan_Zis?> = _saldoper

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    // private val _posts = MutableLiveData<List<Post>>()
    // val posts: LiveData<List<Post>> = _posts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _zisCreated = MutableLiveData<ZisMasuk?>()
    val zisCreated: LiveData<ZisMasuk?> = _zisCreated


    private val _zisResponse = MutableLiveData<ZisMasuk_Respon>()
    val zisrespon: LiveData<ZisMasuk_Respon> = _zisResponse

    private val _zisone = MutableLiveData<ZisMasuk?>()
    val zisone: LiveData<ZisMasuk?> = _zisone

    fun fetchZisRepo() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getZis()
                _zis.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }

    }

    fun fetczissaya(zis: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getZissaya(zis)
                _zis.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun fetcdonasisaya(zis: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getDonasisaya(zis)
                _ziscam.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun fetchistorycampaign(zis: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getHistoryCampaign(zis)
                _hiscam.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun fetchlaporanzis(dari: String, hingga: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = repository.getLaporanzis(dari, hingga)
                _lapzis.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun fetchsaldozis() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getsaldozis()
                if (response.isSuccessful) {
                    _saldo.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                    _saldo.value = null // Atur ke null jika gagal
                }
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.localizedMessage}"
                _saldo.value = null // Atur ke null jika ada exception
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun fetchakumulasizisperiode(dari: String, hingga: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = repository.getakumulasizisperiode(dari, hingga)
                if (response.isSuccessful) {
                    _saldoper.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                    _saldoper.value = null // Atur ke null jika gagal
                }
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.localizedMessage}"
                _saldoper.value = null // Atur ke null jika ada exception
            } finally {
                _isLoading.value = false
            }
        }
    }
    //dari gemini
//    private fun fetchProducts() {
//        viewModelScope.launch {
//            try {
//                val productList = RetrofitClient.instance.getProducts()
//                _products.value = productList
//            } catch (e: Exception) {
//                _errorMessage.value = "Failed to fetch products: ${e.message}"
//            }
//        }
//    }


    fun createZis(
        muzakki: String,
        keterangan: String,
        nominal: Double,
        jenis: String,
        bayar: String,
        kode: String
    ) {
        _isLoading.value = true
        _error.value = null
        _zisCreated.value = null

        // Untuk contoh ini, kita tetapkan userId = 1
        val tanggalnya = LocalDate.parse("2025-06-01")
        val newPost = ZisMasuk(
            Muzakki = muzakki,
            Keterangan = keterangan,
            Nominal = nominal.toDouble(),
            Tanggal = tanggalnya.toString(),
            Nmr_Terima = "",
            Jenis = jenis,
            Mode_Pembayaran = bayar,
            Nmr_Jurnal = "",
            Kode_Program = "Umum",
            Status = "SETOR",
            EDP = kode,
            Hash_Code = "",
            Kode_Dana = "Umum",
            Kode_Campaign = ""
        )

        viewModelScope.launch {
            try {
                val result = repository.createZis(newPost)
                if (result != null) {
                    // Tambahkan post baru ke daftar
                    val currentPosts = _zis.value?.toMutableList() ?: mutableListOf()
                    currentPosts.add(0, result)
                    _zis.value = currentPosts

                    // Set postCreated untuk memberitahu UI
                    _zisCreated.value = result
                } else {
                    _error.value = "Failed to create post"
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun createDonasi(
        muzakki: String,
        keterangan: String,
        nominal: Double,
        jenis: String,
        bayar: String,
        kodedana: String,
        edp: String
    ) {
        _isLoading.value = true
        _error.value = null
        _zisCreated.value = null

        // Untuk contoh ini, kita tetapkan userId = 1
        val tanggalnya = LocalDate.parse("2025-06-01")
        val newPost = ZisMasuk(
            Muzakki = muzakki,
            Keterangan = keterangan,
            Nominal = nominal.toDouble(),
            Tanggal = tanggalnya.toString(),
            Nmr_Terima = "",
            Jenis = jenis,
            Mode_Pembayaran = bayar,
            Nmr_Jurnal = "",
            Kode_Program = kodedana,
            Status = "SETOR",
            EDP = edp,
            Hash_Code = "",
            Kode_Dana = kodedana,
            Kode_Campaign = ""
        )

        viewModelScope.launch {
            try {
                val result = repository.createZis(newPost)
                if (result != null) {
                    // Tambahkan post baru ke daftar
                    val currentPosts = _zis.value?.toMutableList() ?: mutableListOf()
                    currentPosts.add(0, result)
                    _zis.value = currentPosts

                    // Set postCreated untuk memberitahu UI
                    _zisCreated.value = result
                } else {
                    _error.value = "Failed to create post"
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }


    fun uploadbukti(
        kodemuzakki: RequestBody,
        foto: MultipartBody.Part
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _zisone.value = null
            try {
                val response = repository.uploadbuktitransaksi(
                    kodemuzakki,
                    foto
                )
                if (response != null) {
                    _zisResponse.value = response
                    _isLoading.value = false
                } else {
                    _error.value = "Failed to create post"
                }

            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }
}
//gemini langsung ke client
//    fun addProduct(name: String, price: String, imageUrl: String?) {
//        viewModelScope.launch {
//            try {
//                val priceDouble = price.toDoubleOrNull()
//                if (priceDouble == null) {
//                    _addProductResult.value = "Harga tidak valid"
//                    return@launch
//                }
//                val newProduct = Product(name = name, price = priceDouble, imageUrl = imageUrl)
//                val result = RetrofitClient.instance.createProduct(newProduct)
//                _addProductResult.value = "Produk berhasil ditambahkan"
//                fetchProducts() // Refresh daftar setelah menambahkan
//            } catch (e: Exception) {
//                _addProductResult.value = "Gagal menambahkan produk: ${e.message}"
//            }
//        }
//
//
//    }