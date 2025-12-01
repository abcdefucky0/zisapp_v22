package solusiapk.com.zisapp_v2.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.databinding.FragmentLaporanBinding
import solusiapk.com.zisapp_v2.datamodel.Laporan_Zis
import java.text.NumberFormat
import java.util.Locale


class LaporanZisViewHolder(itemView: View, private val laporanList: List<Laporan_Zis>) :
    RecyclerView.ViewHolder(itemView) {

   val judulTextView: TextView = itemView.findViewById(R.id.namaketerangan)
   val isiTextView: TextView = itemView.findViewById(R.id.txtmasuk)
  // val tanggalnya: TextView = itemView.findViewById(R.id.txtkeluar)

    fun bind(zislaporan: Laporan_Zis) {
       judulTextView.text = zislaporan.Jenis_Dana
        var nilai: Int = zislaporan.Masuk.toInt()
        if (nilai == 0) {
            isiTextView.text = ""
        } else {
            val indonesiaLocale = Locale("id", "ID")
            val currencyFormatterIDR = NumberFormat.getCurrencyInstance(indonesiaLocale)
            //println("Formatted IDR (NumberFormat): ${currencyFormatterIDR.format(amountIDR)}")

            isiTextView.text = "${currencyFormatterIDR.format(nilai)}"
            //   tanggalnya.text = "Rp. " + zislaporan.Keluar.toString()
        }

    }
}