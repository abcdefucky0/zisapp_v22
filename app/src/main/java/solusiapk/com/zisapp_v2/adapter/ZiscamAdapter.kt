package solusiapk.com.zisapp_v2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.datamodel.ZisCam_Report
import solusiapk.com.zisapp_v2.viewholder.ZiscamViewHolder

class ZiscamAdapter(private val ziscamList: List<ZisCam_Report>) :
    RecyclerView.Adapter<ZiscamViewHolder>() {

    private var ziscams = listOf<ZisCam_Report>()

    fun setziscam(posts: List<ZisCam_Report>) {
        this.ziscams = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZiscamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ziscam_masuk_item, parent, false) // Ganti dengan nama layout item Anda
        return ZiscamViewHolder(itemView,ziscamList)
    }

    override fun onBindViewHolder(
        holder: ZiscamViewHolder,
        position: Int
    ) {
        val currentzis = ziscamList[position]
        holder.bind(currentzis)
    }

    override fun getItemCount(): Int {
        return ziscamList.size
    }
}