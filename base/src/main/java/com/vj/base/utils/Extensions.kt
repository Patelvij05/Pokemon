package com.vj.base.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import org.apache.commons.codec.binary.StringUtils
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun TextView.setFrame(x: Int, y: Int, width: Int, height: Int) {

    val params = RelativeLayout.LayoutParams(width, height)
    params.leftMargin = x
    params.topMargin = y

    layoutParams = params
}

fun ImageView.setFrame(x: Int, y: Int, width: Int, height: Int) {

    val params = RelativeLayout.LayoutParams(width, height)
    params.leftMargin = x
    params.topMargin = y

    layoutParams = params
}

fun View.setFrame(x: Int, y: Int, width: Int, height: Int) {

    val params = RelativeLayout.LayoutParams(width, height)
    params.leftMargin = x
    params.topMargin = y

    layoutParams = params
}

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

val duration: Long = 200

fun View.animateAlpha(to: Float, from: Float, height: Int, complete: (success: Boolean) -> Unit) {
    val animator = ObjectAnimator.ofFloat(this, "y", from, to)
    animator.duration = duration
    animator.start()

    animator.addListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator?) {}
        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationCancel(animation: Animator?) {
            complete(false)
        }

        override fun onAnimationEnd(animation: Animator?) {
            complete(true)
        }

    })
}

fun String.rotate(n: Int) = drop(n % length) + take(n % length)

fun String.swap(): String {

    var walker = 0
    var encryptText = this
    val textCount = this.count()

    if (textCount > 1) {
        do {
            encryptText = encryptText.swapCharacters(encryptText, walker, walker + 1)
            walker += 2
        } while (walker < textCount - 1)
    }

    return encryptText
}

fun String.swapCharacters(text: String, from: Int, to: Int): String {
    if (from < 0 || from >= text.length || to < 0 || to >= text.length)
        return "Invalid index"

    val arr = text.toCharArray()
    val tmp = arr[from]
    arr[from] = arr[to]
    arr[to] = tmp

    return String(arr)
}

fun Double.currencyFormat(): String {
    val formatter = DecimalFormat("#,##0.00")
    return formatter.format(this)
}

fun String.replaceMobileNumberTHCountryCode(): String {
    val regEx = "[^0-9]".toRegex()
    val digitNumber = this.replace(regEx, "")
    return if (digitNumber.startsWith("66")) {
        digitNumber.replaceFirst("66", "0")
    } else {
        digitNumber
    }
}

fun String.formatToCurrencyFloorValue(): String {
    return NumberFormat
            .getNumberInstance(Locale.getDefault())
            .format(this.toFloat().toInt())
}

fun String.formatToCurrencyWithDecimalValue(): String {
    return try {
        val availableBalance = this.toDouble()
        val decimalFormat = DecimalFormat("#,##0.00")
        decimalFormat.format(availableBalance)
    } catch (e: NumberFormatException) {
        this
    }
}

fun Context.getDrawableResourceByName(imageName: String?): Drawable? {
    val resources = this.resources
    return try {
        if (!imageName.isNullOrEmpty()) {
            val resourceId = resources.getIdentifier(imageName, "drawable", this.packageName)
            ContextCompat.getDrawable(this, resourceId)
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun StringUtils.numericAndAlphabetFilter(): InputFilter = InputFilter { source, start, end, dest, dstart, dend ->
    return@InputFilter source
            .filter { ((Character.isDigit(it)) || ((it in 'A'..'Z') || (it in 'a'..'z'))) }
            .toString()
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}