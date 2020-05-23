package com.vj.base.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.vj.base.BuildConfig
import com.vj.base.R
import com.vj.base.utils.Contextor
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class SuperAppApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {

    private var timer: CountDownTimer? = null
    var activity: Activity? = null
    var isActive: Boolean = false
    var isTimeout: Boolean = false


    override fun onCreate() {
        super.onCreate()
        mInstance = this
        this.registerActivityLifecycleCallbacks(this)

        Contextor.getInstance().init(applicationContext)

        CalligraphyConfig.initDefault(
            CalligraphyConfig
                .Builder()
                .setDefaultFontPath(getString(R.string.font_default))
                .setFontAttrId(R.attr.fontPath)
                .build())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        private var mInstance: SuperAppApplication? = null
        fun get(): SuperAppApplication? {
            return mInstance
        }
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        activity = p0
    }

    override fun onActivityStarted(p0: Activity) {
        activity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        activity = p0
        isActive = true
        if (p0 !is BaseActivity) {
            clearFlagSecure()
        }
    }

    override fun onActivityPaused(p0: Activity) {
        isActive = false
        if (p0 !is BaseActivity) {
            setFlagSecure()
        }
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    private fun setFlagSecure() {
        if (!BuildConfig.DEBUG) {
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    private fun clearFlagSecure() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun startTimer(time: Int?) {
        isTimeout = false
        var timeInterval: Int? = time
        timeInterval = timeInterval?.times(1000)
        stopTimer()
        timer = object : CountDownTimer(timeInterval!!.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                isTimeout = true
                showTimeOut()
            }
        }.start()
    }

    fun showTimeOut() {
        if (isActive && isTimeout) {
            isTimeout = false
            (activity as BaseActivity).alertTimeOut()
        }
    }

    fun showInactiveAccessTokenError() {
        stopTimer()
        (activity as BaseActivity).hideAnimationLoading()
        (activity as BaseActivity).alertTimeOut()
    }

    fun stopTimer() {
        timer?.cancel()
    }
}
