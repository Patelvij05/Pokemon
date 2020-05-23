package com.vj.base.utils

import android.annotation.SuppressLint
import android.content.Context
import java.util.*

class Contextor private constructor() {

    var context: Context? = null
    internal fun init(context: Context) {
        this.context = context
    }

    internal fun setLocale(language: String? = "th"){
        context?.let{ context ->
            val res = context.resources
            val conf = res.configuration
            conf.setLocale(Locale(language))
            init(context.createConfigurationContext(conf))
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: Contextor? = null

        fun getInstance(): Contextor {
            if (instance == null)
                instance = Contextor()
            return instance as Contextor
        }
    }

}
