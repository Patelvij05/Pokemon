package com.vj.pokemon.scene.pokehome

import com.vj.pokemon.scene.pokehome.model.PokeHomeModel
import com.vj.pokemon.scene.pokehome.model.PokemonDetailModel

class PokeHomePresenter {

    lateinit var activity: PokeHomeActivity

    fun showLoading() {
        activity.showLoading()
    }

    fun hideLoading() {
        activity.hideLoading()
    }

    fun presentAllPokemon(rawData: List<PokeHomeModel>) {
        if (rawData != null) {
            activity.setPokemonList(rawData)
        }
    }

    fun presentErrorScreen(){
        activity.displayErrorDialog()
    }

    fun showPokemonDetails(rawData: PokemonDetailModel.Response.PokemonDetail){
        if (rawData != null) {
            activity.setPokemonDetails(rawData)
        }
    }

}