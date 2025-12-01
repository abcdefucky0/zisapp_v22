package solusiapk.com.zisapp_v2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.R
import solusiapk.com.zisapp_v2.datamodel.History_Campaign
import solusiapk.com.zisapp_v2.datamodel.ZisMasuk
import solusiapk.com.zisapp_v2.viewholder.HistoryCampaignViewHolder
import solusiapk.com.zisapp_v2.viewholder.ZisViewHolder

//gemini
class HistoryCampaignAdapter(private val historytList: List<History_Campaign>) :
    RecyclerView.Adapter<HistoryCampaignViewHolder>() {

    private var ziss = listOf<History_Campaign>()

    fun sethistori(posts: List<History_Campaign>) {
        this.ziss = posts
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryCampaignViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_campaign_item, parent, false) // Ganti dengan nama layout item Anda
        return HistoryCampaignViewHolder(itemView)

    }

    override fun onBindViewHolder(
        holder: HistoryCampaignViewHolder,
        position: Int
    ) {
        val currentProduct = historytList[position]
        holder.bind(currentProduct)

    }

    override fun getItemCount(): Int {
        return historytList.size
    }
}

