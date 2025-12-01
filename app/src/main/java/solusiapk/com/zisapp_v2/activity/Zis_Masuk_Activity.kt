package solusiapk.com.zisapp_v2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.ActivityZisMasukBinding
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import kotlin.getValue
import kotlin.jvm.java

class Zis_Masuk_Activity : AppCompatActivity() {

    private val viewModel: ZisViewModel by viewModels()
    private lateinit var binding: ActivityZisMasukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZisMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var namaTerima = intent.getStringExtra("namamuzakki")
        var kodeterima = intent.getStringExtra("Kodemuz")

        namaTerima?.let {
            binding.namapenyetor.setText(namaTerima)
        }
        kodeterima?.let {
            binding.kodemuzakki.setText(kodeterima)
        }

        binding.btnsimpanzis.setOnClickListener {
            simpandonasi()
        }

    }

    fun simpandonasi() {
        val muzakki = binding.kodemuzakki.text.toString().trim()
        val namapenyetor = binding.namapenyetor.text.toString().trim()
        val keterangan = binding.keterangandonasi.text.toString().trim()
        val nominal = binding.nomimaldonasi.text.toString()
        var kodemuzakki = binding.kodemuzakki.text.toString()
        var jenis: String = ""
        var bayar: String = ""

        val checkedbayar = binding.radioGroup2.checkedRadioButtonId
        if (checkedbayar != -1) {
            val radioButton = findViewById<RadioButton>(checkedbayar)
//            val selectedValue = radioButton.text.toString()
            bayar = radioButton.text.toString()

            // Lakukan sesuatu dengan nilai yang dipilih (misalnya, tampilkan di Toast)
            //Toast.makeText(this, "Pilihan: $selectedValue", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Pilih salah satu mode pembayaran", Toast.LENGTH_SHORT).show()
        }

        val cekjenis = binding.radioGroup.checkedRadioButtonId
        if (cekjenis != -1) {
            val pilihjenis = findViewById<RadioButton>(cekjenis)
            jenis = pilihjenis.text.toString()
        } else {
            Toast.makeText(this, "Pilih salah satu Jenis ZIS Anda", Toast.LENGTH_SHORT).show()
        }

//
        // Simple validation
        if (muzakki.isEmpty()) {
            // muzakkinya.error = "Title cannot be empty"
            Toast.makeText(this, "Nama Muzakki harus diisi", Toast.LENGTH_SHORT).show()
        }

        if (keterangan.isEmpty()) {
            //  keterangannya.error = "Body cannot be empty"
            Toast.makeText(this, "Keterangan harus diisi", Toast.LENGTH_SHORT).show()
        }

        try {
//        // Create the post
            // viewModel.createZis(muzakki, keterangan, nominal.toDouble(), jenis, bayar, kodemuzakki)

            var judulextra: String =
                "Terima kasih Bapak/Ibu " + namapenyetor + ", Telah menunaikan " + jenis + " pada UPZ Bakti Bersama. Terus tebarkan kebahagiaan untuk semua"
            val intent = Intent(this, Konfirmasi_Zis_Activity::class.java)

            intent.putExtra("muzakki", muzakki)
            intent.putExtra("penyetor", namapenyetor)
            intent.putExtra("keterangan", keterangan)
            intent.putExtra("nominal", nominal)
            intent.putExtra("jenis", jenis)
            intent.putExtra("bayar", bayar)
            intent.putExtra("kodemuzakki", kodemuzakki)
            intent.putExtra("JUDUL_EXTRA", judulextra)

            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal menyimpan setoran", Toast.LENGTH_SHORT).show()
        }

    }

}