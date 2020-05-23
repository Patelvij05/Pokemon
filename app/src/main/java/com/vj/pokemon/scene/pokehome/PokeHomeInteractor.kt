package com.vj.pokemon.scene.pokehome

import android.util.Log
import com.vj.pokemon.constants.PokemonConstants
import com.vj.pokemon.constants.PokemonConstants.Companion.EMPTY_STRING
import com.vj.pokemon.scene.pokehome.model.PokeHomeModel
import com.vj.pokemon.scene.pokehome.model.PokemonDetailModel
import com.vj.pokemon.scene.pokehome.worker.PokeHomeWorker

class PokeHomeInteractor {

    lateinit var presenter: PokeHomePresenter
    lateinit var worker: PokeHomeWorker
    var pokemonList : ArrayList<PokeHomeModel>? = null
    var pokemonTimeOut = 30L
    var pokemonName: String = EMPTY_STRING
    var pokemonDetails: PokemonDetailModel.Response.PokemonDetail? = null


    fun getPokemonList() {
        presenter.showLoading()
        worker.getPokemonList(onSuccess = {
            presenter.hideLoading()
            pokemonList = it as ArrayList<PokeHomeModel>
            presenter.presentAllPokemon(it)
        }, onFailure = {
            Log.d(it.errorCode, it.errorMessage)
            presenter.hideLoading()
            presenter.presentErrorScreen()
        },timeout = pokemonTimeOut)
    }

    fun getPokemonDetails() {

        presenter.showLoading()
        if (pokemonName.isNotEmpty()) {
            worker.getPokemonInformation(pokemonName, onSuccess = { model ->
                pokemonDetails = model
                presenter.showPokemonDetails(model)
                presenter.hideLoading()
            }, onFailure = {
                Log.d(it.errorCode, it.errorMessage)
                presenter.hideLoading()
                if (PokemonConstants.OK_STATUS.toString() != it.errorCode) {
                    presenter.presentErrorScreen()
                }
            })
        }

    }

}