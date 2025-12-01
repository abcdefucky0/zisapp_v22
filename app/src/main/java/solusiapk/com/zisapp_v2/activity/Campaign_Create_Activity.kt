package solusiapk.com.zisapp_v2.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.api.CampaignClient
import solusiapk.com.zisapp_v2.api.CampaignClient2
import solusiapk.com.zisapp_v2.api.CampaignService2
import solusiapk.com.zisapp_v2.databinding.ActivityCampaignCreateBinding
import solusiapk.com.zisapp_v2.databinding.ActivityMuzakkiBinding
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.datamodel.Usulan
import solusiapk.com.zisapp_v2.factory.CampaignViewModelFactory
import solusiapk.com.zisapp_v2.repository.CampaignRepository2
import solusiapk.com.zisapp_v2.viewmodel.CampaignViewModel2
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.getValue
import kotlin.jvm.java

class Campaign_Create_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: CampaignViewModel2
    private val productRepository = CampaignRepository2(CampaignClient2.apiService)

    //private val productRepository = ProductRepository(RetrofitClient.apiService)
    private lateinit var binding: ActivityCampaignCreateBinding

    private val viewModelmuzakki: MuzakkiViewModel by viewModels()

    private var selectedImageUri: Uri? = null
    private var selectedImageUri2: Uri? = null
    private var selectedImageUri3: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        firebaseAuth = FirebaseAuth.getInstance()

        binding = ActivityCampaignCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            CampaignViewModelFactory(productRepository)
        ).get(CampaignViewModel2::class.java)

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is CampaignViewModel2.UiState.Success -> {
                        Toast.makeText(
                            this@Campaign_Create_Activity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is CampaignViewModel2.UiState.Error -> {
                        Toast.makeText(
                            this@Campaign_Create_Activity,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is CampaignViewModel2.UiState.Loading -> {
                        // Tampilkan progress bar
                    }

                    else -> {}
                }
            }
        }

        binding.btnsimpanusulan.setOnClickListener {
            simpanusulan()
        }

        binding.btnPilihGambar.setOnClickListener {
            pickImageLauncher.launch("image/*") // Membuka galeri
        }

        binding.btnPilihGambar2.setOnClickListener {
            pickImageLauncher2.launch("image/*")
        }

        binding.btnPilihGambar3.setOnClickListener {
            pickImageLauncher3.launch("image/*")
        }
    }


    fun simpanusulan() {
        val namaPenerima = binding.txtnamapenerima.text?.toString() ?: ""
        val jenisBantuan = binding.jenisbantuan.text?.toString() ?: ""
        val besarBantuanText = binding.besarbantuan.text?.toString() ?: "0"
        val deskripsi = binding.deskripsi.text?.toString() ?: ""
        val alamat = binding.alamat.text?.toString() ?: ""

        // Lakukan pengecekan
        if (namaPenerima.isNotEmpty() && jenisBantuan.isNotEmpty() && deskripsi.isNotEmpty() && alamat.isNotEmpty()) {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val userEmail: String?
                userEmail = currentUser.email
                if (!userEmail.isNullOrEmpty()) {
                    val newemail = userEmail.replace('@', 'g')
                    viewModelmuzakki.fetchMuzakkibyEmail(newemail)
                    viewModelmuzakki.user.observe(this) { user ->
                        if (user != null) {
                            if (selectedImageUri != null && selectedImageUri2 != null && selectedImageUri3 != null) {
                                val file = getFileFromUri(
                                    this, selectedImageUri!!,"temp_image1.jpg"
                                )
                                val imagePart = MultipartBody.Part.createFormData(
                                    "gambar",
                                    file.name,
                                    file.asRequestBody("image/*".toMediaTypeOrNull())
                                )

                                val file2 = getFileFromUri(
                                    this, selectedImageUri2!!,"temp_image2.jpg"
                                )
                                val imagePart2 = MultipartBody.Part.createFormData(
                                    "gambar2",
                                    file2.name,
                                    file2.asRequestBody("image/*".toMediaTypeOrNull())
                                )

                                val file3 = getFileFromUri(
                                    this, selectedImageUri3!!,"temp_image3.jpg"
                                )
                                val imagePart3 = MultipartBody.Part.createFormData(
                                    "gambar3",
                                    file3.name,
                                    file3.asRequestBody("image/*".toMediaTypeOrNull())
                                )


                                val namapenerimapart =
                                    namaPenerima.toRequestBody("text/plain".toMediaTypeOrNull())
                                val jenisbantuanpart =
                                    jenisBantuan.toRequestBody("text/plain".toMediaTypeOrNull())
                                val besarbantuanpart =
                                    besarBantuanText.toRequestBody("text/plain".toMediaTypeOrNull())
                                val deskripsipart =
                                    deskripsi.toRequestBody("text/plain".toMediaTypeOrNull())
                                val alamatpart =
                                    alamat.toRequestBody("text/plain".toMediaTypeOrNull())
                                val kodemuzakki = user.Kode_Muzakki.toString()
                                    .toRequestBody("text/plain".toMediaTypeOrNull())

                                try {
                                    viewModel.createCampaign(
                                        kodemuzakki,
                                        namapenerimapart,
                                        jenisbantuanpart,
                                        besarbantuanpart,
                                        deskripsipart,
                                        alamatpart,
                                        imagePart,
                                        imagePart2,
                                        imagePart3
                                    )

                                    viewModel.apiResponse.observe(this) { response ->
                                        response?.let {
                                            Toast.makeText(
                                                this,
                                                response.message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            if (it.status == "success") {
                                                // Pindah ke Activity berikutnya
                                                startActivity(
                                                    Intent(
                                                        this,
                                                        MainActivity::class.java
                                                    )
                                                )
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_SHORT)
                                        .show()
                                }

//                            var jalan = viewModel.createCampaign(usulabaru)
//                            if (jalan != null) {
//                                val intent = Intent(this, MainActivity::class.java)
//                                startActivity(intent)
//                                // true
//                            }
                            } else {
                            }

                        } else {

                        }
                    }
                }

            } else {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        } else {
            // Tampilkan pesan error jika ada field yang kosong
            Toast.makeText(this, "Mohon lengkapi semua data.", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Handler setelah pengguna memilih gambar
        uri?.let {
            selectedImageUri = it
            binding.imgfoto1.setImageURI(it) // Tampilkan gambar yang dipilih di ImageView
        }
    }

    private val pickImageLauncher2 = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Handler setelah pengguna memilih gambar
        uri?.let {
            selectedImageUri2 = it
            binding.imgfoto2.setImageURI(it) // Tampilkan gambar yang dipilih di ImageView
        }
    }

    private val pickImageLauncher3 = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Handler setelah pengguna memilih gambar
        uri?.let {
            selectedImageUri3 = it
            binding.imgfoto3.setImageURI(it) // Tampilkan gambar yang dipilih di ImageView
        }
    }
}

fun getFileFromUri(context: Context, uri: Uri, fileName: String): File {
    val file = File(context.cacheDir, fileName)
    try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return file
}

//fun getFileFromUri(context: Context, uri: Uri): File {
//    val file = File(context.cacheDir, "temp_image.jpg")
//    try {
//        context.contentResolver.openInputStream(uri)?.use { inputStream ->
//            FileOutputStream(file).use { outputStream ->
//                inputStream.copyTo(outputStream)
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return file
//}
//fun getFileFromUri2(context: Context, uri: Uri): File {
//    val file = File(context.cacheDir, "temp_image.jpg")
//    try {
//        context.contentResolver.openInputStream(uri)?.use { inputStream ->
//            FileOutputStream(file).use { outputStream ->
//                inputStream.copyTo(outputStream)
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return file
//}

//fun getFileFromUri3(context: Context, uri: Uri): File {
//    val file = File(context.cacheDir, "temp_image.jpg")
//    try {
//        context.contentResolver.openInputStream(uri)?.use { inputStream ->
//            FileOutputStream(file).use { outputStream ->
//                inputStream.copyTo(outputStream)
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return file
//}