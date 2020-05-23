package com.vj.pokemon.scene.pokehome.worker

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vj.base.model.GeneralError
import com.vj.pokemon.scene.pokehome.model.PokeHomeModel
import com.vj.pokemon.scene.pokehome.model.PokemonDetailModel
import com.vj.pokemon.scene.pokehome.service.PokeHomeService
import org.json.JSONArray
import org.json.JSONObject

class PokeHomeWorker {

    private var pokeHomeService: PokeHomeService = PokeHomeService()

    fun getPokemonList( onSuccess: (result: List<PokeHomeModel>) -> Unit, onFailure: (error: GeneralError) -> Unit, timeout: Any ) {

        pokeHomeService.getPokemons(onSuccess = { result: String ->
            Log.d("jsonData", result)
            var json = JSONArray(result).toString()
            val tokenType = object : TypeToken<List<PokeHomeModel>>() {}.type
            var pokeModel = Gson().fromJson<List<PokeHomeModel>>(json, tokenType)
            onSuccess(pokeModel)
        }, onFailure = { errorCode: String, errorMessage: String ->
            onFailure(GeneralError(errorCode = errorCode, errorMessage = errorMessage))
        })

    }

    fun getPokemonInformation(pokemonName: String, onSuccess: (result: PokemonDetailModel.Response.PokemonDetail) -> Unit
                                , onFailure: (error: GeneralError) -> Unit) {

        pokeHomeService.getPokemonDetail(
            pokemonName, onSuccess = { result: String ->

                val poke = Gson().fromJson(result, PokemonDetailModel.Response.PokemonDetail::class.java)
                onSuccess(poke)
            }, onFailure = { errorCode: String, errorMessage: String ->
                onFailure(GeneralError(errorCode = errorCode, errorMessage = errorMessage))
            })
    }
}