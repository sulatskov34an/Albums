package ru.sulatskov.main.screen.general

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(album: Album, listener: (Album) -> Unit) {
            itemView.title.text = album.title
            itemView.setOnClickListener {
                listener(album)
            }
        }
    }
}

class SimpleDividerItemDecoration(context: Context?) : RecyclerView.ItemDecoration() {
    private lateinit var mDivider: Drawable

    init {
        if (context != null) {
            mDivider = context.getDrawable(R.drawable.items_divider)!!
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight + 10

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}