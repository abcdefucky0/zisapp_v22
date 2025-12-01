package solusiapk.com.zisapp_v2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solusiapk.com.zisapp_v2.datamodel.Muzakki
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import solusiapk.com.zisapp_v2.repository.MuzakkiRepository
import java.time.LocalDate
import java.util.Date
import kotlin.String

class MuzakkiViewModel : ViewModel() {
    private val muzakkiRepository = MuzakkiRepository()

    private val _muzakki = MutableLiveData<Muzakki?>()
    val user: LiveData<Muzakki?> = _muzakki

    private val _zis = MutableLiveData<List<Muzakki>>()
    val zis: LiveData<List<Muzakki>> = _zis

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _muzakkicreated = MutableLiveData<Muzakki?>()
    val muzakkiCreated: LiveData<Muzakki?> = _muzakkicreated

    fun fetchMuzakkibyEmail(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = muzakkiRepository.getMuzakkiOne(email)
                if (response.isSuccessful) {
                    _muzakki.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                    _muzakki.value = null // Atur ke null jika gagal
                }
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.localizedMessage}"
                _muzakki.value = null // Atur ke null jika ada exception
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createMuzakki(
        jenismuzakki: String,
        namamuzakki: String,
        jk: String,
        telepon: String,
        alamat: String,
        email: String
    ) {
        _isLoading.value = true
        _error.value = null
        _muzakkicreated.value = null

        // Untuk contoh ini, kita tetapkan userId = 1
        val tanggalnya = LocalDate.parse("2025-06-01")
        val newPost = Muzakki(
            Jenis_Muzakki = jenismuzakki,
            Kode_Muzakki = "",
            Nama_Muzakki = namamuzakki,
            JK = jk,
            NIK = "",
            Tempat_Lahir = "",
            Tgl_Lahir = tanggalnya.toString(),
            Telepon = telepon,
            Pekerjaan = "",
            Pendidikan = "",
            Alamat = alamat,
            Status = "AKtif",
            Email = email,
            Hash_Code = "",
            Mulai_Terdaftar = ""
        )

        viewModelScope.launch {
            try {
                val result = muzakkiRepository.createMuzakki(newPost)
                if (result != null) {
                    // Tambahkan post baru ke daftar
                    val currentPosts = _zis.value?.toMutableList() ?: mutableListOf()
                    currentPosts.add(0, result)
                    _zis.value = currentPosts

                    // Set postCreated untuk memberitahu UI
                    _muzakkicreated.value = result
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
}