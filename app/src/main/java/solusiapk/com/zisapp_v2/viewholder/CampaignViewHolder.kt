package solusiapk.com.zisapp_v2.viewholder

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.activity.AuthActivity
import solusiapk.com.zisapp_v2.activity.CampaignDonasi_Activity
import solusiapk.com.zisapp_v2.activity.Campaign_Detail_Activity
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.viewmodel.MuzakkiViewModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.getValue
import kotlin.jvm.java

class CampaignViewHolder(itemView: View, private val campaignList: List<Campaign>) :
    RecyclerView.ViewHolder(itemView) {
    private lateinit var firebaseAuth: FirebaseAuth

    val judulTextView: TextView = itemView.findViewById(R.id.textJudul)
   // val isiTextView: TextView = itemView.findViewById(R.id.textIsi)
    val donasi: TextView = itemView.findViewById(R.id.terkumpul)

    //val productImageView: ImageView = itemView.findViewById(R.id.imageViewProduct)
    private val context = itemView.context

    fun bind(campaign: Campaign) {
        judulTextView.text = campaign.title

        // myTextView.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
       // isiTextView.text =
          // HtmlCompat.fromHtml(campaign.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

//        val amountIDR = 1234567.89
        val amountIDR = campaign.donasiterkumpul
        if (amountIDR == null){
            donasi.text = "Total Donasi Terkumpul Rp. : 0"
        } else {
            val indonesiaLocale = Locale("id", "ID")
            val currencyFormatterIDR = NumberFormat.getCurrencyInstance(indonesiaLocale)
            //println("Formatted IDR (NumberFormat): ${currencyFormatterIDR.format(amountIDR)}")

            donasi.text = "Total Donasi Terkumpul : ${currencyFormatterIDR.format(amountIDR)}"
        }
        //        productPriceTextView.text = "Rp ${product.price}"
//        product.imageUrl?.let {
//            Glide.with(itemView.context)
//                .load(it)
//                .placeholder(R.drawable.ic_launcher_foreground) // Placeholder jika gambar gagal dimuat
//                .error(R.drawable.ic_launcher_background) // Gambar error jika gagal memuat
//                .into(productImageView)
//        } ?: run {
//            productImageView.setImageResource(R.drawable.ic_launcher_foreground) // Gambar default jika imageUrl null
//        }

        itemView.setOnClickListener {
//            showOptionsDialog(campaign)
//            val posisi = adapterPosition
            val posisi = bindingAdapterPosition
            if (posisi != RecyclerView.NO_POSITION) {
                val namaTerpilih = campaignList[posisi].title
                val kodepilih = campaignList[posisi].id

                val intent = Intent(context, Campaign_Detail_Activity::class.java)

                intent.putExtra("Kodepilih", kodepilih.toString())
                context.startActivity(intent)

//                firebaseAuth = FirebaseAuth.getInstance()
//                val currentUser = firebaseAuth.currentUser
//                if (currentUser == null) {
//                    navigateToAuthActivity()
//                } else {
//                    //updateUI(currentUser)
//                    val currentUser = firebaseAuth.currentUser
//
//                    if (currentUser != null) {
//
//                        val userEmail: String?
//                        userEmail = currentUser.email
//                        if (!userEmail.isNullOrEmpty()) {
//                            val newemail = userEmail.replace('@', 'g')
//                            // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()
//
//
//                            // Intent untuk membuka Activity baru
//                            val intent = Intent(context, CampaignDonasi_Activity::class.java)
//
//                            // Mengirim data ke Activity baru (opsional)
//                            intent.putExtra("email", newemail)
//                            intent.putExtra("Kodepilih", kodepilih.toString())
//                            intent.putExtra("judulterpilih", namaTerpilih)
//
//                            // Memulai Activity baru
//                            context.startActivity(intent)
//                        }
//
//                    }
//                }

            }
        }

    }

    private fun updateUI(user: FirebaseUser?) {
//        val currentUser = firebaseAuth.currentUser
//
//        if (currentUser != null) {
//
//            val userEmail: String?
//            userEmail = currentUser.email
//            if (!userEmail.isNullOrEmpty()) {
//                val newemail = userEmail.replace('@', 'g')
//                // Toast.makeText(this, newemail, Toast.LENGTH_SHORT).show()
//
//
//                // Intent untuk membuka Activity baru
//                val intent = Intent(context, CampaignDonasi_Activity::class.java)
//
//                // Mengirim data ke Activity baru (opsional)
//                intent.putExtra("NAMA_EXTRA", newemail)
//
//                // Memulai Activity baru
//                context.startActivity(intent)
//            }
//
//        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent) // Panggil startActivity dari Context yang diterima

    }

    //        val textViewNama: TextView = itemView.findViewById(R.id.txtkodecampaign)
//


//    private fun showOptionsDialog(product: Campaign) {
//        itemView.setOnClickListener {
//            val posisi = adapterPosition
//            if (posisi != RecyclerView.NO_POSITION) {
//                val namaTerpilih = listNama[posisi].nama
//
//                // Intent untuk membuka Activity baru
//                val intent = Intent(context, DetailNamaActivity::class.java)
//
//                // Mengirim data ke Activity baru (opsional)
//                intent.putExtra("NAMA_EXTRA", namaTerpilih)
//
//                // Memulai Activity baru
//                context.startActivity(intent)
//            }
//        }
//    }

}