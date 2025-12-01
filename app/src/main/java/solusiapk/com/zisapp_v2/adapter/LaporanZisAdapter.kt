package solusiapk.com.zisapp_v2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.datamodel.Laporan_Zis
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import solusiapk.com.zisapp_v2.viewholder.LaporanZisViewHolder
import solusiapk.com.zisapp_v2.viewholder.ZisViewHolder

class LaporanZisAdapter(private val lapList: List<Laporan_Zis>) :
    RecyclerView.Adapter<LaporanZisViewHolder>() {

    private var laps = listOf<Laporan_Zis>()

    fun setlapzis(posts: List<Laporan_Zis>) {
        this.laps = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LaporanZisViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laporan_zis, parent, false) // Ganti dengan nama layout item Anda
        return LaporanZisViewHolder(itemView,lapList)
    }

    override fun onBindViewHolder(
        holder: LaporanZisViewHolder,
        position: Int
    ) {
        val currentzis = lapList[position]
        holder.bind(currentzis)
    }

    override fun getItemCount(): Int {
        return lapList.size
    }
}