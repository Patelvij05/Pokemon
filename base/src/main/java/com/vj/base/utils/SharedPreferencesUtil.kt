package com.vj.base.utils

import android.preference.PreferenceManager
import com.vj.base.utils.Contextor

object SharedPreferencesUtil {

    private val sharedPreference = PreferenceManager.getDefaultSharedPreferences(Contextor.getInstance().context)!!

    fun getString(key: String, defVal: String) : String? {
        return sharedPreference.getString(key, defVal)
    }

    fun putString(key: String, value : String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    fun clear() {
        sharedPreference.edit().clear().apply()
    }

}