package com.vj.base.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject

class BaseHttpLogger(aesKey: String): HttpLoggingInterceptor.Logger  {

    var aesKey: String = ""
    init {
        this.aesKey = aesKey
    }
    override fun log(message: String?) {
        val logName = "OkHttp"
        if (!message!!.startsWith("{")) {
            Log.d(logName, message)
            return
        }
        try {
            val prettyPrintJson = GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(message))
            if (aesKey == "") {
                Log.d("$logName Body ", JSONObject(prettyPrintJson).toString())
            } else {
                Log.d("$logName Body ", prettyPrintJson.toString())
            }
        } catch (m: JsonSyntaxException) {
            Log.d(logName, message)
        } catch (m: JSONException){
            Log.d(logName, message)
        }

    }

}