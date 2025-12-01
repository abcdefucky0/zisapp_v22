package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityCampaignDetailBinding
import solusiapk.com.zisapp_v2.viewmodel.CampaignViewModel
import kotlin.getValue
import com.google.firebase.auth.FirebaseUser

class Campaign_Detail_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCampaignDetailBinding
    private val viewModel: CampaignViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    var kodeatas: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampaignDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var kode: String? = intent.getStringExtra("Kodepilih")
        var id: Int? = kode?.toIntOrNull()

        val safeId: Int = id ?: 0
        viewModel.fetcampaignone(safeId)

        viewModel.campaignone.observe(this) { camp ->
            if (camp != null) {
                binding.judulcampaign.text = camp.title
                binding.detailcampaign.text =
                    HtmlCompat.fromHtml(camp.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }

        kodeatas = kode?.toIntOrNull() ?: 0
//        Toast.makeText(this, kodeatas.toString(), Toast.LENGTH_SHORT).show()

        binding.donasiya.setOnClickListener {
            mulaidonasi()
        }
    }

    fun mulaidonasi() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            navigateToAuthActivity()
        } else {
//                    //updateUI(currentUser)
            val currentUser = firebaseAuth.currentUser

            if (currentUser != null) {
//
                val userEmail: String?
                userEmail = currentUser.email
                if (!userEmail.isNullOrEmpty()) {
                    val newemail = userEmail.replace('@', 'g')
//                            // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, CampaignDonasi_Activity::class.java)
//
//                            // Mengirim data ke Activity baru (opsional)
                   // var kode: String? = intent.getStringExtra("Kodepilih")
                    var id: Int? = kodeatas

                    val safeId: Int = id ?: 0

                    viewModel.fetcampaignone(safeId)

                    var judul: String = ""
                    viewModel.campaignone.observe(this) { camp ->
                        if (camp != null) {
                            judul = camp.title
                        }
                    }

                    intent.putExtra("email", newemail)
                    intent.putExtra("Kodepilih", kodeatas.toString())

                    intent.putExtra("judulterpilih", judul)

//
//                            // Memulai Activity baru
                    this.startActivity(intent)
                }
//
            }
        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent) // Panggil startActivity dari Context yang diterima

    }
}