package com.vj.pokemon.scene.pokehome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vj.pokemon.R
import com.vj.pokemon.scene.pokehome.model.PokeHomeModel
import kotlinx.android.synthetic.main.item_poke_list.view.*

class PokeHomeAdapter(
    private val dataList: List<PokeHomeModel>,
    private val context: Context,
    private val listener: PokemonListener
) :
    RecyclerView.Adapter<PokeHomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_poke_list, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = dataList[position].name
        holder.container.setOnClickListener {
            listener.onPokemonClickedListener(dataList[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.tvName!!
        val container = itemView.pokemonContainer!!
    }

    interface PokemonListener {
        fun onPokemonClickedListener(pokemon: PokeHomeModel)
    }
}