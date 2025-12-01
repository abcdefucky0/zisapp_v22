package solusiapk.com.zisapp_v2.viewholder

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.activity.CampaignDonasi_Activity
import solusiapk.com.zisapp_v2.activity.History_Campaign_Activity
import solusiapk.com.zisapp_v2.datamodel.ZisCam_Report
import java.text.NumberFormat
import java.util.Locale
import kotlin.jvm.java

class ZiscamViewHolder(itemView: View, private val ziscamList: List<ZisCam_Report>) :
    RecyclerView.ViewHolder(itemView) {

    private val context = itemView.context

    val judulTextView: TextView = itemView.findViewById(R.id.namadonasiziscam)
    val isiTextView: TextView = itemView.findViewById(R.id.nominaldonasihziscam)
    val tanggal: TextView = itemView.findViewById(R.id.tglziscam)


    //    //val productImageView: ImageView = itemView.findViewById(R.id.imageViewProduct)
//
    fun bind(ziscam: ZisCam_Report) {
        judulTextView.text = ziscam.title

        val amountIDR =ziscam.Nominal
        val indonesiaLocale = Locale("id", "ID")
        val currencyFormatterIDR = NumberFormat.getCurrencyInstance(indonesiaLocale)
        //println("Formatted IDR (NumberFormat): ${currencyFormatterIDR.format(amountIDR)}")

        isiTextView.text = "Besaran Donasi : ${currencyFormatterIDR.format(amountIDR)}"

        tanggal.text = "Tanggal : " + ziscam.Tanggal + "      Status : " + ziscam.Status


        itemView.setOnClickListener {
            val posisi = bindingAdapterPosition
            if (posisi != RecyclerView.NO_POSITION) {
                val namaTerpilih = ziscamList[posisi].title
                val kodepilih = ziscamList[posisi].Kode_Dana

//                // Intent untuk membuka Activity baru
              val intent = Intent(context, History_Campaign_Activity ::class.java)
//
//                // Mengirim data ke Activity baru (opsional)
               intent.putExtra("NAMA_EXTRA", namaTerpilih)
               intent.putExtra("Kodepilih", kodepilih.toString())
//
//                // Memulai Activity baru
                context.startActivity(intent)
            }
        }
    }


}