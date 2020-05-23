package com.vj.base.application

import android.app.ProgressDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.vj.base.R
import com.vj.base.customview.PokemonBaseToolbar

open class BaseDialogFragment: DialogFragment(), PokemonBaseToolbar.ToolbarListener {


    private var loadingDialog: ProgressDialog? = null

    fun setToolbarListener(baseToolbar: PokemonBaseToolbar){
        baseToolbar.setListener(this@BaseDialogFragment)
    }

    override fun onBackPressed() {
        this.dismiss()
    }

    override fun onButtonRightTextClick() {
    }

    fun showAnimationLoading() {
        loadingDialog?.dismiss()
        loadingDialog = ProgressDialog.show(context, "", getString(R.string.loading), true, false)
    }

    fun hideAnimationLoading() {
        loadingDialog?.dismiss()
    }

    override fun onFinishScreen() {
        this.dismiss()
    }

    fun showDialogAllowingStateLoss(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

    }
}