package com.example.pokedex.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.data.model.PokemonDTO
import com.example.pokedex.databinding.ItemPokemonBinding
import java.util.*
import kotlin.math.round

class PokemonAdapter(private val context: Context?,  private val onClick: (PokemonDTO) -> Unit) : PagingDataAdapter<PokemonDTO, PokemonAdapter.ViewHolder>(PokemonComparator) {

    class ViewHolder(itemPokemonBiding: ItemPokemonBinding) : RecyclerView.ViewHolder(itemPokemonBiding.root) {

        private val pokemonName: TextView = itemPokemonBiding.pokemonName
        private val pokemonId: TextView = itemPokemonBiding.pokemonId
        private val pokemonImage: ImageView = itemPokemonBiding.imageViewPokemon

        fun bind(pokemonDTO: PokemonDTO){
            pokemonName.text = pokemonDTO.name.capitalize(Locale.ROOT)
            pokemonId.text = pokemonDTO.getPokemonIdWithHashTag()
            pokemonImage.load(pokemonDTO.urlImagePokemon) {
                crossfade(true)
            }
        }
    }

    object PokemonComparator : DiffUtil.ItemCallback<PokemonDTO>() {
        override fun areItemsTheSame(oldItem: PokemonDTO, newItem: PokemonDTO): Boolean {
            // Id is unique.
            return oldItem.idPokemon == newItem.idPokemon
        }

        override fun areContentsTheSame(oldItem: PokemonDTO, newItem: PokemonDTO): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val biding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(biding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onClick(it1) }
        }
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ItemDecoration(private val size: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                   // top = size
                }
                left = size
                right = size
                bottom = size
            }
        }
    }
}