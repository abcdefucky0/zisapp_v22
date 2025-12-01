package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityKonfirmasiZisBinding
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel

class Konfirmasi_Zis_Activity : AppCompatActivity() {
    private val viewModel: ZisViewModel by viewModels()
    private lateinit var binding: ActivityKonfirmasiZisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKonfirmasiZisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var muzakki = intent.getStringExtra("muzakki")
        var penyetor = intent.getStringExtra("penyetor")
        val keterangan = intent.getStringExtra("keterangan")
        var nominal = intent.getStringExtra("nominal").toString()
        val jenis = intent.getStringExtra("jenis")
        val bayar = intent.getStringExtra("bayar")
        val kodemuzakki = intent.getStringExtra("kodemuzakki")
        val judul = intent.getStringExtra("JUDUL_EXTRA")

        binding.btnkembalidonasi.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        muzakki?.let {
            binding.nkodedonatur.text = muzakki
        }

        penyetor?.let {
            binding.ndonatur.text = penyetor
        }
        jenis?.let {
            binding.ndonasi.text = jenis
        }
        bayar?.let {
            binding.nmetode.text = bayar
        }
        nominal?.let {
            binding.nnominal.text = nominal
        }
        keterangan?.let {
            binding.nketerangan.text = keterangan
        }

        val nilaiDonasiDouble: Double = nominal.toDoubleOrNull() ?: 0.0

        binding.btnsimpandonasi.setOnClickListener {
            try {
                viewModel.createZis(
                    muzakki.toString(),
                    keterangan.toString(),
                    nilaiDonasiDouble.toDouble(),
                    jenis.toString(),
                    bayar.toString(),
                    kodemuzakki.toString()
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