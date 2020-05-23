package com.vj.pokemon.scene.pokehome.model

import com.google.gson.annotations.SerializedName
import com.vj.pokemon.model.Ability
import com.vj.pokemon.model.Move
import com.vj.pokemon.model.Sprite

class PokemonDetailModel {

    class Request() {}

    class Response {
        data class PokemonDetail(
            @SerializedName("id") var id: Int? = null,
            @SerializedName("name") var name: String? = null,
            @SerializedName("sprites") var sprites: Sprite? = null,
            @SerializedName("moves") var moves: ArrayList<Move>? = null,
            @SerializedName("weight") var weight: Int? = null,
            @SerializedName("height") var height: Int? = null,
            @SerializedName("base_experience") var base_experience: Int? = null,
            @SerializedName("abilities") var abilities: ArrayList<Ability>? = null
        )
    }

    class DisplayedPokemonResult {}
}