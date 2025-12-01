package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityProfileBinding

class Profile_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data yang dikirim dari Intent

        var namaTerima = intent.getStringExtra("namamuzakki")
        var kodeterima = intent.getStringExtra("Kodemuz")
        val jenis = intent.getStringExtra("jenismuz")
        val jk = intent.getStringExtra("jk")
        var telepon = intent.getStringExtra("telepon")
        var alamat = intent.getStringExtra("alamat")

        namaTerima?.let {
            binding.namamuzakki.setText(namaTerima)
        }
        kodeterima?. let{
            binding.kodemuzakki.setText(kodeterima)
        }
        jenis?. let{
            binding.jenismuzakki.setText(jenis)
        }
        jk?. let{
            binding.jeniskelamin.setText(jk)
        }
        telepon?. let{
            binding.telepon.setText(telepon)
        }
        alamat?. let{
            binding.alamat.setText(alamat)
        }

        binding.btnkembali.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}