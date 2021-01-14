package ru.sulatskov.main.screen.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_photo.view.*
import ru.sulatskov.R
import ru.sulatskov.common.getProgressBar

class SliderImageAdapter(private val context: Context) :
    RecyclerView.Adapter<SliderImageAdapter.ViewHolder>() {

    private val images: MutableList<String?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_photo_full,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position], context)
    }

    override fun getItemCount() = images.size

    fun setData(items: List<String?>) {
        images.clear()
        images.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(path: String?, context: Context) {
            itemView.photo_iv?.let {
                Picasso.get()
                    .load(path)
                    .error(R.drawable.error)
                    .placeholder(getProgressBar(context))
                    .fit()
                    .into(it)
            }
        }
    }
}