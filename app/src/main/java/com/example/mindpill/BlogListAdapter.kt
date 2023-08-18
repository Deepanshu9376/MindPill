package com.example.mindpill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BlogListAdapter(private val listener: MainActivity): RecyclerView.Adapter<BlogsViewHolder>() {

    private val items: ArrayList<Blogs> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_blogs, parent, false)
        val viewHolder = BlogsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BlogsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun updateBlogs(updateBlogs: ArrayList<Blogs>) {
        items.clear()
        items.addAll(updateBlogs)

        notifyDataSetChanged()
    }
}

class BlogsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface BlogItemClicked {
    fun onItemClicked(item: Blogs)
}
