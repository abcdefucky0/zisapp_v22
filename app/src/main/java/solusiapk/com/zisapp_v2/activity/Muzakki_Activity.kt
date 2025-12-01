package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityMainBinding
import solusiapk.com.zisapp_v2.databinding.ActivityMuzakkiBinding
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import java.util.Date
import kotlin.getValue

class Muzakki_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMuzakkiBinding
    private val viewModel: MuzakkiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_muzakki)
        binding = ActivityMuzakkiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnsimpanmuzakki.setOnClickListener {
            simpanmuzakki()
        }
    }

    fun simpanmuzakki() {
        var email: String = ""
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {

            val userEmail: String?
            userEmail = currentUser.email
            if (!userEmail.isNullOrEmpty()) {
                val newemail = userEmail.replace('@', 'g')
                // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()
                email = newemail
            }

        }


        var Jenis_Muzakki: String = ""
        val Nama_Muzakki = binding.namamuzakki.text.toString()
        var JK: String = ""
        val Telepon = binding.telepon.text.toString()
        val Alamat = binding.alamat.text.toString()

        val checkedbayar = binding.radioGroupjm.checkedRadioButtonId
        if (checkedbayar != -1) {
            val radioButton = findViewById<RadioButton>(checkedbayar)
            Jenis_Muzakki = radioButton.text.toString()

            // Lakukan sesuatu dengan nilai yang dipilih (misalnya, tampilkan di Toast)
            //Toast.makeText(this, "Pilihan: $selectedValue", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Pilih salah satu Jenis Muzakki", Toast.LENGTH_SHORT).show()
        }

        val checkejk = binding.radioGroupjk.checkedRadioButtonId
        if (checkejk != -1) {
            val radioButton = findViewById<RadioButton>(checkejk)
            JK = radioButton.text.toString()

            // Lakukan sesuatu dengan nilai yang dipilih (misalnya, tampilkan di Toast)
            //Toast.makeText(this, "Pilihan: $selectedValue", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Pilih salah satu Jenis Kemalin", Toast.LENGTH_SHORT).show()
        }

//
        // Simple validation
        if (Nama_Muzakki.isEmpty()) {
           // Nama_Muzakki. = "Title cannot be empty"
            Toast.makeText(this, "Nama Muzakki harus diisi", Toast.LENGTH_SHORT).show()
        }

        if (Telepon.isEmpty()) {
           // keterangannya.error = "Body cannot be empty"
            Toast.makeText(this, "Telepon harus diisi", Toast.LENGTH_SHORT).show()
        }

        if (Alamat.isEmpty()) {
            // keterangannya.error = "Body cannot be empty"
            Toast.makeText(this, "Alamat harus diisi", Toast.LENGTH_SHORT).show()
        }

        try {
//        // Create the post
            viewModel.createMuzakki(
                Jenis_Muzakki,
                Nama_Muzakki,
                JK,
                Telepon,
                Alamat,
                email
            )
//            Nama_Muzakki.text.clear()
//            keterangannya.text.clear()
//            nominalnya.text.clear()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Memulai Activity kedua
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal menyimpan Muzakki", Toast.LENGTH_SHORT).show()
        }

    }
}