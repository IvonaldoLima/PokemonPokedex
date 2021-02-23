package com.example.pokedex.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.pokedex.Constants.Companion.EXTRA_POKEMON
import com.example.pokedex.databinding.ActivityPokemonDetailsBinding
import com.example.pokedex.ui.viewmodel.PokemonDetailsActivityViewModel
import com.example.pokedex.util.Resource.Status.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsActivity : AppCompatActivity() {

    private val viewModel by viewModels<PokemonDetailsActivityViewModel>()
    private lateinit var binding: ActivityPokemonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateLayoutActivity()
        loadPokemon()
        viewModel.pokemonLiveData.observe(this, {
            when(it.status){
                LOADING ->{
                    Log.d("IPL2", "Carregando: $it")
                }
                SUCCESS ->{
                    Log.d("IPL2", "Sucesso: $it")
                    it.data?.apply {
                        binding.pokemonDetailsName.text = name
                        binding.pokemonDetailImage.load(getUrlImage()) {
                            crossfade(true)
                        }
                        binding.pokemonsDetailsHpProgressBar.setProgress(hp, true)
                        binding.pokemonDetailsHpValue.text = "$hp/300"
                        binding.pokemonsDetailsAtkProgressBar.setProgress(attack, true)
                        binding.pokemonDetailsAtkValue.text = "$attack/300"
                        binding.pokemonsDetailsDefProgressBar.setProgress(defense, true)
                        binding.pokemonDetailsDefValue.text = "$defense/300"
                        binding.pokemonsDetailsSpdProgressBar.setProgress(speed, true)
                        binding.pokemonDetailsSpdValue.text = "$speed/300"
                    }
                }
                ERROR -> {
                    Log.d("IPL2", "Erro: $it")
                }
            }
        })
    }

    private fun inflateLayoutActivity(){
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun loadPokemon(){
        var pokemonId = getExtra()
        binding.pokemonId.text = pokemonId.toString()
        viewModel.getPokemonDetails(pokemonId)
    }

    private fun getExtra(): Int {
        intent.getIntExtra(EXTRA_POKEMON, 0).apply {
            return this
        }
    }
}