package solusiapk.com.zisapp_v2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import solusiapk.com.zisapp_v2.viewholder.CampaignViewHolder
import solusiapk.com.zisapp_v2.viewholder.ZisViewHolder

class ZisAdapter(private val zisList: List<ZisMasuk>) :
    RecyclerView.Adapter<ZisViewHolder>() {

    private var ziss = listOf<ZisMasuk>()

    fun setzis(posts: List<ZisMasuk>) {
        this.ziss = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZisViewHolder {
         val itemView = LayoutInflater.from(parent.context)
           .inflate(R.layout.zis_masuk_item, parent, false) // Ganti dengan nama layout item Anda
         return ZisViewHolder(itemView,zisList)


    }

    override fun onBindViewHolder(
        holder: ZisViewHolder,
        position: Int
    ) {
        val currentzis = zisList[position]
        holder.bind(currentzis)
    }

    override fun getItemCount(): Int {

        return zisList.size
    }



}