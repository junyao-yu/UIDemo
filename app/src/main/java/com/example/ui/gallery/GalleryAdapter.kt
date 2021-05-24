package com.example.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R

/**
 * Created by yujunyao@xinrenlei.net on 5/24/21.
 */
class GalleryAdapter(var list: List<String>): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, null))
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var gallery: ImageView? = null;

        init {
            gallery = itemView.findViewById(R.id.galleryImageView)
        }

    }
}


