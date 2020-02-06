package ru.sulatskov.main.screen.general

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_album.view.*
import ru.sulatskov.R
import ru.sulatskov.model.network.Album

class AlbumsAdapter(private val listener: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private val albums = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_album,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(albums[position], listener)
    }

    override fun getItemCount() = albums.size

    fun setData(items: List<Album>) {
        albums.clear()
        albums.addAll(items)
        notifyDataSetChanged()
    }

    fun getList() = albums

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(album: Album, listener: (Album) -> Unit) {
            itemView.title.text = album.title
            itemView.setOnClickListener {
                listener(album)
            }
        }
    }
}