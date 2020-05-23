package com.vj.base.utils


import androidx.annotation.Nullable

object BuildConfigHelper {

    private val BUILD_CONFIG = "com.vj.pokemon.utils.BuildConfigHelper"

    val BASE_URL: String = getBuildConfigValue("BASE_URL") as String

    @Nullable
    private fun getBuildConfigValue(fieldName: String): Any? {
        try {
            val c = Class.forName(BUILD_CONFIG)
            val f = c.getDeclaredField(fieldName)
            f.isAccessible = true
            return f.get(null)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}
