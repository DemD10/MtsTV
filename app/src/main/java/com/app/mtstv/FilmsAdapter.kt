package com.app.mtstv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.FilmDomain
import com.app.mtstv.databinding.RcItemPosterBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class FilmsAdapter : ListAdapter<FilmDomain, FilmsAdapter.FilmViewHolder>(FilmsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            RcItemPosterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class FilmViewHolder(private val binding: RcItemPosterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(filmDomain: FilmDomain) {
            binding.posterAppCompatImageView.loadImageFromUrl(filmDomain.poster)
        }
    }

    private object FilmsDiffUtil : DiffUtil.ItemCallback<FilmDomain>() {
        override fun areItemsTheSame(old: FilmDomain, new: FilmDomain) = old.id == new.id

        override fun areContentsTheSame(old: FilmDomain, new: FilmDomain) = old == new
    }
}