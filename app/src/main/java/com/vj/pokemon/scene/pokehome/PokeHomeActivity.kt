package com.vj.pokemon.scene.pokehome

import android.graphics.Typeface
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vj.base.application.BaseActivity
import com.vj.base.customview.PokemonBaseBottomAlertDialog
import com.vj.base.customview.dialog.BottomAlertDialogContent
import com.vj.pokemon.R
import com.vj.pokemon.scene.pokehome.model.PokeHomeModel
import com.vj.pokemon.scene.pokehome.model.PokemonDetailModel
import kotlinx.android.synthetic.main.activity_poke_home.*

open class PokeHomeActivity : BaseActivity(), PokeHomeAdapter.PokemonListener {

    lateinit var interactor: PokeHomeInteractor
    lateinit var router: PokeHomeRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poke_home)
    }

    override fun onResume() {
        super.onResume()
        initialInstances()
    }

    private fun initialInstances() {
        PokeHomeConfigure.configure(this)
        interactor.getPokemonList()
    }

    fun showLoading() {
        this.showAnimationLoading()
    }

    fun hideLoading() {
        this.hideAnimationLoading()
    }

    fun setPokemonList(response: List<PokeHomeModel>) {
        setPokemonListAdapter(rvPokemon, response)
    }

    private fun setPokemonListAdapter(recyclerView: RecyclerView?, response: List<PokeHomeModel>) {
        recyclerView?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView?.adapter = PokeHomeAdapter(response, this, this)
    }

    fun displayErrorDialog() {

        val title = BottomAlertDialogContent(
            getString(R.string.common_header_error_message2),
            R.dimen.default_text_size,
            R.color.black_light,
            null,
            null,
            null,
            Typeface.BOLD
        )

        val subTitle = BottomAlertDialogContent(
            getString(R.string.common_error_message2),
            R.dimen.default_text_size_extra_extra_small,
            R.color.warm_grey_three,
            null,
            null,
            null
        )

        val okButton = BottomAlertDialogContent(
            getString(R.string.common_accept_button),
            R.dimen.default_text_size_smaller,
            R.color.white,
            null,
            R.drawable.selector_button_default,
            onClickAction = {
                PokemonBaseBottomAlertDialog.dialog.dismiss()
                onBackPressed()
            })

        val buttonList: List<BottomAlertDialogContent> = arrayListOf(okButton)

        val alert = PokemonBaseBottomAlertDialog

        alert.alertDialog(
            context = this
            , image = ""
            , title = title
            , subTitle = subTitle
            , link = null
            , buttons = buttonList)
    }

    fun displayBottomSheet(response: PokemonDetailModel.Response.PokemonDetail){

        val name = response.name
        val height = "Height: " + response.height.toString() +
                     " Metre\n\nWeight: " + response.weight.toString() + " Kilogram" +
                     "\n\nBase experience: " + response.base_experience.toString()

        val title = BottomAlertDialogContent(
            name,
            R.dimen.default_text_size,
            R.color.black_light,
            null,
            null,
            null,
            Typeface.BOLD
        )

        val subTitle = BottomAlertDialogContent(
            height,
            R.dimen.default_text_size_extra_extra_small,
            R.color.warm_grey_three,
            null,
            null,
            null
        )

        val okButton = BottomAlertDialogContent(
            getString(R.string.common_accept_button),
            R.dimen.default_text_size_smaller,
            R.color.white,
            null,
            R.drawable.selector_button_default,
            onClickAction = {
                PokemonBaseBottomAlertDialog.dialog.dismiss()
            })

        val buttonList: List<BottomAlertDialogContent> = arrayListOf(okButton)

        val alert = PokemonBaseBottomAlertDialog

        alert.alertDialog(
            context = this
            , image = response.sprites?.frontImage
            , title = title
            , subTitle = subTitle
            , link = null
            , buttons = buttonList)

    }

    fun setPokemonDetails(response: PokemonDetailModel.Response.PokemonDetail) {
        displayBottomSheet(response)
    }

    override fun onPokemonClickedListener(pokemon: PokeHomeModel) {
        interactor.pokemonName = pokemon.name!!
        interactor.getPokemonDetails()
    }

}