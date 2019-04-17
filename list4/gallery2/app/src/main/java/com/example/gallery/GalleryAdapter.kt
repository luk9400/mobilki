package com.example.gallery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_gallery_image.view.*

class GalleryAdapter(private val itemList: ArrayList<Image>) : RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {
    lateinit var listener: GalleryImageClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_image, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }


    override fun getItemCount() = itemList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val image = itemList[adapterPosition]
            Picasso.get().load(image.imageUrl).into(itemView.imageView)
            itemView.setOnClickListener {
                listener.click(adapterPosition)
            }
        }
    }
}
