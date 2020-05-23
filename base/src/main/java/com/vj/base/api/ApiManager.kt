package com.vj.base.api

import android.util.Log
import com.vj.base.BuildConfig
import com.vj.base.constant.ErrorMessage
import com.vj.base.constant.HttpMethod
import com.vj.base.constant.ResponseCode
import com.vj.base.utils.BuildConfigHelper
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

private var listener: ApiManager.ApiManagerListener? = null

class ApiManager(val timeout: Long = 30L) {

    private var retrofit: Retrofit? = null
    private var baseUrl: String = ""
    private var aesKey: String = ""
    private var headerCommon = hashMapOf<String, String>()

    init {
        baseUrl = BuildConfigHelper.BASE_URL
        initHeader()
    }

    private fun initHeader() {
        with(headerCommon) {

        }
    }

    fun <T> call(serviceRequestClass: Class<T>): T {
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .client(OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .addNetworkInterceptor(httpLogging(""))
                .build())
            .build()
        return retrofit!!.create(serviceRequestClass)
    }

    fun setCallBack(call: Call<ResponseBody>, isDisplayDefaultAlert: Boolean? = true, onSuccess: (jsonData: String) -> Unit, onFailure: (errorCode: String, errorMessage: String, jsonData: String) -> Unit) {
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    val dataResponse = response.body()!!.string()
                    onSuccess(dataResponse)
                } else {
                    val dataResponse = response.errorBody()!!.string()
                    try {
                        val results = JSONObject(dataResponse).getString("results")
                        val json = JSONObject(results)
//                        val messageCode = json.getString("code")
//
//                        if (response.code() == ResponseCode.BAD_REQUEST) {
//                            when(messageCode) {
//
//                            }
//                        }

                        if (isDisplayDefaultAlert!!) {
                            verifyGeneralError(response.code())
                        } else {
                            //onFailure("" + response.code(), messageCode, aesDecrypt)
                        }
                    } catch (ex: Exception) {
                        if (isDisplayDefaultAlert!!) {
                            verifyGeneralError(response.code())
                        } else {
                            onFailure("" + response.code(), ErrorMessage.UNKNOWN, "")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (isDisplayDefaultAlert!!) {
                    verifyGeneralError(-1)
                } else {
                    if (t is SocketTimeoutException) {
                        onFailure("", ErrorMessage.TIMEOUT, "")
                    } else {
                        onFailure("", ErrorMessage.UNKNOWN, "")
                    }
                }
            }
        })
    }

    class GenericInterceptor(private val header: HashMap<String, String>, private val body: String) : Interceptor {

        override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {
            val request = chain!!.request()
            val newRequest = request.newBuilder()

            for ((key, value) in header) {
                newRequest.addHeader(key, value)
            }
            if (body.isNotEmpty()) {
                when (request.method()) {
                    HttpMethod.POST -> newRequest.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                    HttpMethod.PUT -> newRequest.put(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                    HttpMethod.DELETE -> newRequest.delete(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                }

            }
            newRequest.removeHeader("Pragma")
            return chain.proceed(newRequest.build())
        }
    }

    private fun httpLogging(aesKey: String): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor(BaseHttpLogger(aesKey = aesKey)).setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    private fun verifyGeneralError(code: Int) {
        when (code) {
            ResponseCode.BAD_REQUEST, ResponseCode.UNAUTHORIZED, ResponseCode.FORBIDDEN, ResponseCode.NOT_FOUND, ResponseCode.METHOD_NOT_ALLOWED -> {
                listener?.onShowGeneralErrorDialog()
            }
            ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.SERVICE_UNAVAILABLE, ResponseCode.GATEWAY_TIMEOUT -> {
                listener?.onShowGeneralErrorDialog()
            }
            else -> listener?.onShowGeneralErrorDialog()
        }
    }


    companion object {
        fun setAPIManagerListener(listeners: ApiManagerListener) {
            listener = listeners
        }
    }

    interface ApiManagerListener {
        fun onShowGeneralErrorDialog()
        fun onShowGeneralErrorBottomDialog()
    }

}
