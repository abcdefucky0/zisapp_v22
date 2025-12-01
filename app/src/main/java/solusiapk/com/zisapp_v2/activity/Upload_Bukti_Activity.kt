package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityCampaignCreateBinding
import solusiapk.com.zisapp_v2.databinding.ActivityUploadBuktiBinding
import solusiapk.com.zisapp_v2.factory.CampaignViewModelFactory
import solusiapk.com.zisapp_v2.viewmodel.CampaignViewModel2
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import kotlin.getValue

class Upload_Bukti_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModelmuzakki: MuzakkiViewModel by viewModels()

    private val viewModel: ZisViewModel by viewModels()
    private lateinit var binding: ActivityUploadBuktiBinding
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        firebaseAuth = FirebaseAuth.getInstance()

        binding = ActivityUploadBuktiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnPilihGambarbukti.setOnClickListener {
            pickImageLauncher.launch("image/*") // Membuka galeri
        }

        binding.btnsimpanbukti.setOnClickListener {
            simpanupload()
        }
//        setContentView(R.layout.activity_upload_bukti)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    fun simpanupload() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userEmail: String?
            userEmail = currentUser.email
            if (!userEmail.isNullOrEmpty()) {
                val newemail = userEmail.replace('@', 'g')
                viewModelmuzakki.fetchMuzakkibyEmail(newemail)
                viewModelmuzakki.user.observe(this) { user ->
                    if (user != null) {
                        if (selectedImageUri != null) {
                            val file = getFileFromUri(
                                this, selectedImageUri!!,"temp_image1.jpg"
                            )
                            val imagePart = MultipartBody.Part.createFormData(
                                "gambar",
                                file.name,
                                file.asRequestBody("image/*".toMediaTypeOrNull())
                            )

                            val kodemuzakki = user.Kode_Muzakki.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull())

                            try {
                                viewModel.uploadbukti(
                                    kodemuzakki,
                                    imagePart
                                )

                                viewModel.zisrespon.observe(this) { response ->
                                    response?.let {
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                        if (it.status == "success") {
                                            // Pindah ke Activity berikutnya
                                            startActivity(Intent(this, MainActivity::class.java))
                                        }
                                    }
                                }

                            } catch (e: Exception) {
                                Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        } else {
                            Toast.makeText(this, "Foto Tidak ada", Toast.LENGTH_SHORT).show()
                        }

                    } else {

                    }
                }
            }

        } else {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }



    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Handler setelah pengguna memilih gambar
        uri?.let {
            selectedImageUri = it
            binding.imgfotobukti.setImageURI(it) // Tampilkan gambar yang dipilih di ImageView
        }
    }
}