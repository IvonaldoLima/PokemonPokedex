package com.example.pokedex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.model.PokemonDTO
import com.example.pokedex.databinding.ItemPokemonBinding

class PokemonAdapter(private val context: Context?) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val setPokemonDTO: MutableList<PokemonDTO> = mutableListOf()

    class ViewHolder(itemPokemonBiding: ItemPokemonBinding) : RecyclerView.ViewHolder(itemPokemonBiding.root) {

        private val pokemonName: TextView = itemPokemonBiding.pokemonName
        fun bind(pokemonDTO: PokemonDTO){
            pokemonName.text = pokemonDTO.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val biding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(biding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonDTO: PokemonDTO = setPokemonDTO[position]
        holder.bind(pokemonDTO)
    }

    override fun getItemCount(): Int = setPokemonDTO.size

    fun add(pokemonDTO: PokemonDTO){
        setPokemonDTO.add(pokemonDTO)
        notifyDataSetChanged()
    }

    fun addAll(pokemonDTOS: List<PokemonDTO>){
        setPokemonDTO.clear()
        setPokemonDTO.addAll(pokemonDTOS)
        notifyDataSetChanged()
    }
}