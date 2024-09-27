package com.bangkit.fixmyrideapp.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.fixmyrideapp.data.response.NewsData
import com.bangkit.fixmyrideapp.databinding.ItemNewsBinding
import com.bangkit.fixmyrideapp.view.detailnews.DetailActivity
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val news: MutableList<NewsData> = mutableListOf()

    class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsData) {
            binding.tvTittleNews.text = news.title
            binding.tvDescNews.text = news.description
            binding.tvTypeNews.text = news.type
            Glide.with(itemView.context)
                .load(news.photo_url)
                .into(binding.imgNews)
        }

    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val news = news[position]
        holder.bind(news)

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.LINK_URL, news.link_url)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun submitList(newNews: List<NewsData>) {
        news.clear()
        news.addAll(newNews)
        notifyDataSetChanged()
    }

}