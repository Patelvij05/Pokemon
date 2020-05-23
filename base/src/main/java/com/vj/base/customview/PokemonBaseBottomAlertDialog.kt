package com.vj.base.customview

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.vj.base.R
import com.vj.base.customview.dialog.BaseBottomAlertDialogFragment
import com.vj.base.customview.dialog.BottomAlertDialogContent

object PokemonBaseBottomAlertDialog {
    val dialog = BaseBottomAlertDialogFragment()

    fun alertDialog(
        context: FragmentActivity,
        image: String?,
        title: BottomAlertDialogContent?,
        subTitle: BottomAlertDialogContent?,
        link: BottomAlertDialogContent?,
        buttons: List<BottomAlertDialogContent>?,
        isCancelable: Boolean = false,
        linksList: List<BottomAlertDialogContent>? = null) {
        try {
            dialog.image = image
            dialog.title = title
            dialog.subTitle = subTitle
            dialog.link = link
            dialog.buttons = buttons
            dialog.isCancelable = isCancelable
            dialog.linkList = linksList

            if(!dialog.isAdded) {
                dialog.show(context.supportFragmentManager, "BaseBottomAlertDialogFragment")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun alertSimpleDialog(context: AppCompatActivity, image: Int?, title: Int, subTitle: Int, buttonTitle: Int = R.string.ok, callback: (() -> Unit)? = null) {

        val dialogTitle = BottomAlertDialogContent(
                context.resources.getString(title),
                R.dimen.default_text_size,
                R.color.black,
                null,
                null,
                null)

        val dialogSubTitle = BottomAlertDialogContent(
                context.resources.getString(subTitle),
                R.dimen.default_text_size_extra_small,
                R.color.warm_grey,
                null,
                null,
                null)

        val okButton = BottomAlertDialogContent(
                context.resources.getString(buttonTitle),
                R.dimen.default_text_size_smaller,
                R.color.white,
                null,
                R.drawable.selector_button_default_2,
                onClickAction = {
                    dialog.dismiss()
                    callback.let { it?.invoke() }
                }
        )

//        alertDialog(context, image, dialogTitle, dialogSubTitle, null, listOf(okButton))
    }

}
