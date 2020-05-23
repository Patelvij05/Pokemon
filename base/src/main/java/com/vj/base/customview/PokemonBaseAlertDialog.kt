package com.vj.base.customview

import android.app.AlertDialog
import android.content.Context

class PokemonBaseAlertDialog {

    fun alertDialog(context: Context, message: String?,  buttonMessage: String?, onSuccess: () -> Unit) {

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(buttonMessage) { dialog, id ->
            onSuccess()
            dialog.dismiss()
        }
        builder.show()
    }

    fun alertDialog(context: Context, title: String?="", message: String?="",  buttonPositiveMessage: String?="", onPositive: (() -> Unit)? = null) {

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttonPositiveMessage) { dialog, id ->
           onPositive.let { onPositive?.invoke() }
            dialog.dismiss()
        }
        builder.show()
    }

    fun alertDialog(context: Context, title: String?="", message: String?="",  buttonPositiveMessage: String?="", buttonNegativeMessage: String?="", onPositive: (() -> Unit)? = null
                    , onNegative: (() -> Unit)? = null) {

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttonPositiveMessage) { dialog, id ->
            onPositive.let { onPositive?.invoke() }
            dialog.dismiss()
        }
        builder.setNegativeButton(buttonNegativeMessage){ dialog, which ->
            onNegative.let { onNegative?.invoke() }
            dialog.dismiss()
        }
        builder.show()
    }
}