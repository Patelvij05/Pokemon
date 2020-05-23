package com.vj.pokemon.service

import com.vj.pokemon.constants.PokemonConstants.Companion.API_VERSION_2
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PokemonService {

    @GET("$API_VERSION_2/pokemon")
    fun getPokemons(): Call<ResponseBody>

    @GET("$API_VERSION_2/pokemon/{name}")
    fun getPokemonDetail(@Path("name") name: String?): Call<ResponseBody>
}