package solusiapk.com.zisapp_v2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import solusiapk.com.zisapp_v2.R

class PhotoSlideAdapter(private val photoList: List<Int>) :
    RecyclerView.Adapter<PhotoSlideAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPhoto: ImageView = itemView.findViewById(R.id.imageViewPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo_slide, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoResId = photoList[position]
        Glide.with(holder.itemView.context)
            .load(photoResId)
            .into(holder.imageViewPhoto)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}