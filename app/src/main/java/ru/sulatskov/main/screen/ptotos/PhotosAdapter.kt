package ru.sulatskov.main.screen.ptotos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_photo.view.*
import ru.sulatskov.R
import ru.sulatskov.common.getProgressBar
import ru.sulatskov.model.network.Photo

class PhotosAdapter(private val listener: (Photo) -> Unit) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val photos = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(photos[position], listener)
    }

    override fun getItemCount() = photos.size

    fun setData(items: List<Photo>) {
        photos.clear()
        photos.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(photo: Photo, listener: (Photo) -> Unit) {
            itemView.photo_iv?.apply {
                val path = photo.url+".jpg"
                Glide.with(itemView.context)
                    .load(path)
                    .error(R.drawable.error)
                    .placeholder(getProgressBar(context))
                    .into(itemView.photo_iv)
            }
            itemView.setOnClickListener {
                listener(photo)
            }
        }
    }
}