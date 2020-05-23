package com.vj.pokemon.scene.pokehome

import com.vj.pokemon.scene.pokehome.worker.PokeHomeWorker

class PokeHomeConfigure {

    companion object Singleton {
        fun configure(activity: PokeHomeActivity) {
            val router = PokeHomeRouter()
            router.activity = activity

            val presenter = PokeHomePresenter()
            presenter.activity = activity

            val interactor = PokeHomeInteractor()
            interactor.presenter = presenter
            interactor.worker = PokeHomeWorker()

            activity.interactor = interactor
            activity.router = router
        }
    }
}