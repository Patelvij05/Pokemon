package com.vj.base.application

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.vj.base.BlurDialog
import com.vj.base.BuildConfig
import com.vj.base.R
import com.vj.base.api.ApiManager
import com.vj.base.customview.PokemonBaseAlertDialog
import com.vj.base.customview.PokemonBaseBottomAlertDialog
import com.vj.base.customview.PokemonBaseToolbar

open class BaseActivity : AppCompatActivity(),
        PokemonBaseToolbar.ToolbarListener,
        ApiManager.ApiManagerListener {

    private var loadingDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiManagerListener()
    }

    override fun onPause() {
        super.onPause()
        setFlagSecure()
    }

    override fun onResume() {
        super.onResume()
        clearFlagSecure()
    }

    private fun setFlagSecure() {
        if (!BuildConfig.DEBUG) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    protected fun clearFlagSecure() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun setToolbarListener(toolbar: PokemonBaseToolbar) {
        toolbar.setListener(this@BaseActivity)
    }

    private fun setApiManagerListener() {
        ApiManager.setAPIManagerListener(this@BaseActivity)
    }

    override fun onShowGeneralErrorDialog() {
        hideAnimationLoading()
        PokemonBaseAlertDialog()
                .alertDialog(this@BaseActivity
                        , message = getString(R.string.message_general_error)
                        , buttonPositiveMessage = getString(R.string.ok))
    }

    override fun onShowGeneralErrorBottomDialog() {
        PokemonBaseBottomAlertDialog.alertSimpleDialog(this
                , R.drawable.ic_bottom_alert_dialog
                , R.string.unable_to_proceed_title
                , R.string.please_try_again_later_description
                , R.string.ok
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
    }

    fun getDeviceScreenWidth(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getDeviceScreenHeight(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun showAnimationLoading() {
        loadingDialog?.dismiss()
        loadingDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false)
    }

    fun hideAnimationLoading() {
        loadingDialog?.dismiss()
    }

    fun hideKeyboard(view: View?) {
        val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun hideKeyboard() {
        val view = currentFocus
        val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onFinishScreen() {
        finish()
    }

    override fun onButtonRightTextClick() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setApiManagerListener()
    }

    fun alertTimeOut() {
        val blurDialogFragment = BlurDialog()
        blurDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen)
        blurDialogFragment.show(supportFragmentManager, "BlurDialogFragment")
    }

    fun recreateScreenWithAnimation(enterAnimation: Int, exitAnimation: Int, bundles: Bundle? = null) {
        bundles?.let {
            this.intent.apply { putExtras(bundles) }
        }
        startActivity(this.intent)
        finish()
        this.overridePendingTransition(enterAnimation, exitAnimation)
    }

}
