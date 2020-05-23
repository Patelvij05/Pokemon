package com.vj.pokemon.scene.pokehome.service

import android.util.Log
import com.vj.base.api.ApiManager
import com.vj.pokemon.service.PokemonService
import org.json.JSONObject

class PokeHomeService {
    fun getPokemons(onSuccess: (String) -> Unit, onFailure: (String, String) -> Unit) {

        val api = ApiManager()
        val request = api.call(PokemonService::class.java)

        api.setCallBack(
            call = request.getPokemons(),
            isDisplayDefaultAlert = false,
            onSuccess =  {
                    jsonData: String ->
                Log.d("jsonData", jsonData)

                val jsonData = JSONObject(jsonData).getString("results")
                onSuccess(jsonData)
            },
            onFailure =  { errorCode: String, errorMessage : String, jsonData: String ->
                onFailure(errorCode, errorMessage)
            })
    }

    fun getPokemonDetail(pokemonName: String?
                           , onSuccess: (result: String) -> Unit
                           , onFailure: (errorCode: String, errorMessage: String) -> Unit) {

        val api = ApiManager()

        val request = api.call(PokemonService::class.java)
        api.setCallBack(
            call = request.getPokemonDetail(pokemonName), isDisplayDefaultAlert = false,
            onSuccess = { jsonData: String ->
                Log.d("jsonData", jsonData)
                onSuccess(jsonData)
            },
            onFailure = { errorCode: String, errorMessage: String, jsonData: String ->
                onFailure(errorCode, errorMessage)
            })
    }
}