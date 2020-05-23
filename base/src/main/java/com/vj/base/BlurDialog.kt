package com.vj.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vj.base.R
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment

class BlurDialog: SupportBlurDialogFragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_session_timeout, container)
    }

    override fun getBlurRadius(): Int {
        return 10
    }

    override fun isDimmingEnable(): Boolean {
        return true
    }
}