package solusiapk.com.zisapp_v2.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.datamodel.History_Campaign

class HistoryCampaignViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textTitle: TextView = itemView.findViewById(R.id.tanggalproses)
    private val textBody: TextView = itemView.findViewById(R.id.keteranganproses)

    fun bind(post: History_Campaign) {
        textTitle.text = post.Tanggal
        textBody.text = post.Keterangan
    }
}
