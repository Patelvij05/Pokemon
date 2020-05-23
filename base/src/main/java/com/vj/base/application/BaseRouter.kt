package com.vj.base.application

import android.app.Activity
import android.content.Intent
import com.vj.base.R

open class BaseRouter {

    @Suppress("OverridingDeprecatedMember")
    fun nextScreen(activity: Activity, intent: Intent, nextType: NextType, requestCode: Int? = null, isCloseAllActivity: Boolean? = false) {
        if (isCloseAllActivity!!)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        if (requestCode == null) {
            activity.startActivity(intent)
            displayScreenType(activity, nextType)
        } else {
            activity.startActivityForResult(intent, requestCode!!)
            displayScreenType(activity, nextType)
        }
    }


    private fun displayScreenType(activity: Activity, type: NextType) {
        when (type) {
            NextType.PUSH -> activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
            NextType.PRESENT -> activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            NextType.POP -> activity.overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_right)
        }
    }
}

enum class NextType(val type: Int) {
    PRESENT(1), PUSH(2), POP(3)
}
