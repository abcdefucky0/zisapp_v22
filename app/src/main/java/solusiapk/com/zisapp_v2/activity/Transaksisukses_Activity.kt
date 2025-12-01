package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityHistoryCampaignBinding
import solusiapk.com.zisapp_v2.databinding.ActivityTransaksisuksesBinding
import kotlin.jvm.java

class Transaksisukses_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityTransaksisuksesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksisuksesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var judul = intent.getStringExtra("JUDUL_EXTRA")
        judul?.let {
            binding.terimakasih.text = judul
        }

        binding.btnkembalikehome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        binding.btnuploadbukti.setOnClickListener {
            val intent = Intent(this, Upload_Bukti_Activity ::class.java)

            startActivity(intent)
        }
    }
}