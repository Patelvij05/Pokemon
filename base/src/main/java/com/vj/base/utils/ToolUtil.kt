package com.vj.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.NetworkInterface
import java.util.regex.Matcher
import java.util.regex.Pattern

object ToolUtil {

    private const val TYPE_WIFI = "WIFI"
    private const val TYPE_CELLULAR = "Cellular"
    private const val HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>"

    @SuppressLint("HardwareIds")
    fun getDeviceId() = Settings.Secure.getString(Contextor.getInstance().context!!.contentResolver, Settings.Secure.ANDROID_ID)!!

    fun getIpAddress(): String {
        var ipv4 = ""
        var ipv6 = "0.0.0.0"
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (item in interfaces) {
                for (addr in item.inetAddresses) {
                    if (!addr.isLoopbackAddress) {
                        if (addr is Inet4Address) ipv4 = addr.hostAddress
                        val hostAddr = addr.hostAddress

                        if (addr is Inet6Address) {
                            when(getNetworkType()) {
                                TYPE_WIFI -> {
                                    if (hostAddr.contains("%wlan0"))
                                        ipv6 = hostAddr.substringBeforeLast("%wlan0")
                                }

                                TYPE_CELLULAR -> {
                                    if (addr.scopeId == 0 && item.displayName != "wlan0") ipv6 = hostAddr
                                }
                            }
                        }
                    }
                }
            }
        } catch (ignore: Exception) {
        }
        if (ipv4.isEmpty()) return ipv6
        return ipv4
    }

    fun getNetworkType(): String {
        val connMgr = Contextor.getInstance().context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return if (ConnectivityManager.TYPE_WIFI == activeInfo?.type)
            TYPE_WIFI
        else
            TYPE_CELLULAR
    }

    fun getIsNetworkConnected(): Boolean {
        val connMgr = Contextor.getInstance().context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return activeInfo != null && activeInfo.isConnected
    }

    fun isTextContainsHtml(text: String): Boolean {

        var pattern: Pattern? = null
        val matcher: Matcher
        pattern = Pattern.compile(HTML_TAG_PATTERN);
        matcher = pattern.matcher(text);
        return matcher.find()

    }

}