package com.example.mytoptenapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytoptenapp.databinding.AppItemBinding.*

class Adapter(val appsList: ArrayList<Entry>): RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    class ItemViewHolder (val binding: AppItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(AppItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            appTitle_tv.text = appsList[position].title
        }
    }

    override fun getItemCount() = appsList.size
}