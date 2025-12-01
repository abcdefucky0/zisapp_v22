package solusiapk.com.zisapp_v2.activity

import android.R.attr.text
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import solusiapk.com.zisapp_v2.viewmodel.ZisViewModel
import kotlin.getValue
import kotlin.jvm.java

class CampaignDonasi_Activity : AppCompatActivity() {
    private val viewModel: ZisViewModel by viewModels()
    private val viewModelmuzakki: MuzakkiViewModel by viewModels()

    private lateinit var btnsimpan: Button
    private lateinit var muzakkinya: EditText
    private lateinit var keterangannya: EditText
    private lateinit var nominalnya: EditText
    private lateinit var kodecampaign: TextView
    private lateinit var modebayarnya: RadioGroup
    private lateinit var donatur: EditText
    private lateinit var namacampaign: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_campaign_donasi)

        namacampaign = findViewById(R.id.txtkodecampaign)
       // val textid: TextView = findViewById(R.id.idcampaign)
        btnsimpan = findViewById(R.id.btnsimpandonasi)

        muzakkinya = findViewById(R.id.namadonatur)
        keterangannya = findViewById(R.id.keterangandonasi)
        nominalnya = findViewById(R.id.nomimaldonasi)
       kodecampaign = findViewById(R.id.idcampaigndonasi)
        modebayarnya = findViewById(R.id.radioGroupdonasi)
        donatur = findViewById(R.id.kodedonatur)

        // Mendapatkan data yang dikirim dari Intent

        val newemail = intent.getStringExtra("email").toString()
        viewModelmuzakki.fetchMuzakkibyEmail(newemail)
        viewModelmuzakki.user.observe(this) { user ->
            if (user != null) {
                var namanya = user.Nama_Muzakki
                var kodemuz = user.Kode_Muzakki


                val kodeDiterima = intent.getStringExtra("Kodepilih")
                val judulcamp = intent.getStringExtra("judulterpilih")
                // Menampilkan data di TextView

                kodeDiterima?.let {
                   kodecampaign.text = kodeDiterima
                  // kodecampaign.text = "ini nomornya"
                }

               // kodecampaign.text = "ini nomornya"

                judulcamp?.let {
                    namacampaign.text = judulcamp
                    //namacampaign.text = "ini judulnya"
                }
                muzakkinya.setText(namanya)
                donatur.setText(kodemuz)


            } else {
                val intent = Intent(this, Muzakki_Activity::class.java)
                startActivity(intent)
            }

        }


        btnsimpan.setOnClickListener {
            simpandonasi()

        }
    }

    fun simpandonasi() {
        var namacm = namacampaign.text.toString()

        val muzakki = muzakkinya.text.toString().trim()
        val keterangan = "Donasi Program " + namacm + ", " + keterangannya.text.toString().trim()
        val nominal = nominalnya.text.toString()
        val kodecampaign = kodecampaign.text.toString().trim()
        var jenis: String = "Donasi Campaign"
        var bayar: String = ""
        var edp = donatur.text.toString()


        val checkedbayar = modebayarnya.checkedRadioButtonId
        if (checkedbayar != -1) {
            val radioButton = findViewById<RadioButton>(checkedbayar)
//            val selectedValue = radioButton.text.toString()
            bayar = radioButton.text.toString()

            // Lakukan sesuatu dengan nilai yang dipilih (misalnya, tampilkan di Toast)
            //Toast.makeText(this, "Pilihan: $selectedValue", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Pilih salah satu mode pembayaran", Toast.LENGTH_SHORT).show()
        }


//
        // Simple validation
        if (muzakki.isEmpty()) {
            muzakkinya.error = "Title cannot be empty"
            Toast.makeText(this, "Nama Muzakki harus diisi", Toast.LENGTH_SHORT).show()
        }

        if (keterangan.isEmpty()) {
            keterangannya.error = "Body cannot be empty"
            Toast.makeText(this, "Keterangan harus diisi", Toast.LENGTH_SHORT).show()
        }

        try {
//        // Create the post
//            viewModel.createDonasi(
//                muzakki,
//                keterangan,
//                nominal.toDouble(),
//                jenis,
//                bayar,
//                kodecampaign,
//                edp
//            )
            muzakkinya.text.clear()
            keterangannya.text.clear()
            nominalnya.text.clear()


            val intent = Intent(this, Konfirmasi_Donasi_Activity::class.java)
            var judulextra: String =
                "Terima kasih Bapak/Ibu " + muzakki + ", Dalam Program Campaign " + namacm + ". Terus tebarkan kebahagiaan untuk semua"
            intent.putExtra("muzakki", muzakki)
            intent.putExtra("keterangan", keterangan)
            intent.putExtra("nominal", nominal)
            intent.putExtra("jenis", jenis)
            intent.putExtra("bayar", bayar)
            intent.putExtra("kodecampaign", kodecampaign)
            intent.putExtra("edp", edp)
            intent.putExtra("judulextra", judulextra)
            startActivity(intent) // Memulai Activity kedua
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal menyimpan setoran", Toast.LENGTH_SHORT).show()
        }

    }
}