package ru.sulatskov.main.screen.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*
import ru.sulatskov.R
import ru.sulatskov.common.getProgressBar

class SlidingImageAdapter(context: Context, images: List<String?>) : PagerAdapter() {

    var inflater: LayoutInflater = LayoutInflater.from(context)
    var img = images

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return img.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imageLayout = inflater?.inflate(R.layout.slidingimages_layout, container, false)
        val imageView = imageLayout?.findViewById<ImageView>(R.id.photo_iv)
        imageView?.apply {

            val path = img.get(position)
            Picasso.with(context)
                .load(path)
                .error(R.drawable.error)
                .placeholder(getProgressBar(context))
                .fit()
                .centerCrop()
                .into(photo_iv)
        }
        container.addView(imageLayout, 0)
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }
}