package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityKonfirmasiDonasiBinding
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import kotlin.jvm.java

class Konfirmasi_Donasi_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityKonfirmasiDonasiBinding
    private val viewModel: ZisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKonfirmasiDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        var muzakki = intent.getStringExtra("muzakki")
        var muzakki = intent.getStringExtra("edp")
        var keterangan = intent.getStringExtra("keterangan")
        var nominal = intent.getStringExtra("nominal").toString()
        var jenis = intent.getStringExtra("jenis")
        var bayar = intent.getStringExtra("bayar")
        var kodecampaign = intent.getStringExtra("kodecampaign")
        var edp = intent.getStringExtra("edp")
        var judul = intent.getStringExtra("judulextra")

        muzakki?.let {
            binding.ndonatur.text = muzakki
        }

        keterangan?.let {
            binding.ndonasi.text = keterangan
        }

        bayar?.let {
            binding.nmetode.text = bayar
        }

        nominal?.let {
            binding.nnominal.text = nominal
        }
        binding.btnkembalidonasi.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val nilaiDonasiDouble: Double = nominal.toDoubleOrNull() ?: 0.0

        binding.btnsimpandonasi.setOnClickListener {
            try {
                // Create the post
                viewModel.createDonasi(
                    muzakki.toString(),
                    keterangan.toString(),
                    nilaiDonasiDouble,
                    jenis.toString(),
                    bayar.toString(),
                    kodecampaign.toString(),
                    edp.toString()
                )


                val intent = Intent(this, Transaksisukses_Activity::class.java)

                intent.putExtra("JUDUL_EXTRA", judul)
                startActivity(intent) // Memulai Activity kedua
            } catch (e: Exception) {
                Toast.makeText(this, "Gagal menyimpan setoran", Toast.LENGTH_SHORT).show()
            }
        }
    }
}