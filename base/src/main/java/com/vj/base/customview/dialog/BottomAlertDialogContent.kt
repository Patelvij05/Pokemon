package com.vj.base.customview.dialog

import android.graphics.Typeface

data class BottomAlertDialogContent(
        val text: String? = "",
        val fontSizeDimen: Int,
        val fontColor: Int,
        val fontTypeface: Typeface?,
        val bgResource: Int?,   //button bgResource
        val onClickAction: (() -> Unit)? = null,
        val fontTypeFaceStyle: Int? = null
) {

    var isAllCap: Boolean = true

    fun setIsAllCap(isAllCap: Boolean) {
        this.isAllCap = isAllCap
    }
}
