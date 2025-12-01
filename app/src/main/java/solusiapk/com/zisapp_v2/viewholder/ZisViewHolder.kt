package solusiapk.com.zisapp_v2.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import java.text.NumberFormat
import java.util.Locale

class ZisViewHolder(itemView: View, private val zisList: List<ZisMasuk>) :
    RecyclerView.ViewHolder(itemView) {
    val judulTextView: TextView = itemView.findViewById(R.id.namadonasi)
    val isiTextView: TextView = itemView.findViewById(R.id.nominaldonasih)
    val tanggal: TextView = itemView.findViewById(R.id.tanggalsetor)
    val tandaterima: TextView = itemView.findViewById(R.id.ketuk)
//val judul: TextView = itemView.findViewById(R.id.julsetor)
    //    //val productImageView: ImageView = itemView.findViewById(R.id.imageViewProduct)
//
    fun bind(zismasuk: ZisMasuk) {
        judulTextView.text = zismasuk.Keterangan

    val amountIDR = zismasuk.Nominal
    val indonesiaLocale = Locale("id", "ID")
    val currencyFormatterIDR = NumberFormat.getCurrencyInstance(indonesiaLocale)
    //println("Formatted IDR (NumberFormat): ${currencyFormatterIDR.format(amountIDR)}")

        isiTextView.text = "Besar Setoran ${currencyFormatterIDR.format(amountIDR)}"
        tanggal.text = "Tanggal : " + zismasuk.Tanggal + "    Status : " + zismasuk.Status
    tandaterima.text ="Ketuk Item untuk Download Bukti Setoran"
       // judul.text = "Riwayat Setoran ZIS Anda :"
    }
}