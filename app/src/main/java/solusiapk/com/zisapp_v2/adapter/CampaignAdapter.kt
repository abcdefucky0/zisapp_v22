package solusiapk.com.zisapp_v2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import solusiapk.com.zisapp_v2.R
import androidx.recyclerview.widget.RecyclerView
import solusiapk.com.zisapp_v2.datamodel.Campaign
import solusiapk.com.zisapp_v2.viewholder.CampaignViewHolder

class CampaignAdapter(private val campaignList: List<Campaign>) :
    RecyclerView.Adapter<CampaignViewHolder>() {

    private var campaigns = listOf<Campaign>()

    fun setCampaigns(posts: List<Campaign>) {
        this.campaigns = posts
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CampaignViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.campaign_item, parent, false) // Ganti dengan nama layout item Anda
        return CampaignViewHolder(itemView,campaignList)

    }

    override fun onBindViewHolder(
        holder: CampaignViewHolder,
        position: Int
    ) {
        val currentCampaign = campaignList[position]
        holder.bind(currentCampaign)

    }

    override fun getItemCount(): Int {
        return campaignList.size
    }
}