package com.vp.commonaddons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vp.commonaddons.ListAdapter.ListViewHolder
import com.vp.commonaddons.model.ListItem

class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {

    companion object {
        private const val NO_IMAGE = "N/A"
    }

    private var listItems: MutableList<ListItem> = mutableListOf()
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.common_images_list, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, _, _, poster) = listItems[position]
        if (NO_IMAGE != poster) {
            val density = holder.image.resources.displayMetrics.density
            Glide.with(holder.image)
                    .load(poster)
                    .apply(RequestOptions().override((300 * density).toInt(), (600 * density).toInt()))
                    .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun setItems(listItems: List<ListItem>) {
        this.listItems = listItems.toMutableList()
        notifyDataSetChanged()
    }

    fun clearItems() {
        listItems.clear()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var image: ImageView
        override fun onClick(v: View) {
            onItemClickListener?.onItemClick(listItems[adapterPosition].imdbID)
        }

        init {
            itemView.setOnClickListener(this)
            image = itemView.findViewById(R.id.poster)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(imdbID: String)
    }
}