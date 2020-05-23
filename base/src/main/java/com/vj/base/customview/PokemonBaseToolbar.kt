package com.vj.base.customview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.item_toolbar.view.*
import com.vj.base.R.styleable.PokemonBaseToolbar_android_text
import com.vj.base.R.styleable.PokemonBaseToolbar_showBackButton
import com.vj.base.R.styleable.PokemonBaseToolbar_showCloseButton
import com.vj.base.R.styleable.PokemonBaseToolbar_showRightButton
import com.vj.base.R.styleable.PokemonBaseToolbar_textRightButton
import com.vj.base.R.styleable.PokemonBaseToolbar_showBottomBarLine
import com.vj.base.R.styleable.PokemonBaseToolbar_iconLeftButton
import com.vj.base.R.styleable.PokemonBaseToolbar_iconRightButton
import android.graphics.drawable.Drawable
import com.vj.base.R


class PokemonBaseToolbar : FrameLayout {

    private var listener: ToolbarListener? = null
    private var isCloseBtn = false

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        setup(attrs)
    }


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        setup(attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context!!, attrs, defStyleAttr, defStyleRes) {
        setup(attrs)
    }


    @SuppressLint("Recycle")
    private fun setup(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.item_toolbar, this@PokemonBaseToolbar)
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PokemonBaseToolbar)
        updateView(typeArray)
        buttonBack.setOnClickListener {
            if (isCloseBtn) {
                listener!!.onFinishScreen()
            } else {
                listener!!.onBackPressed()
            }
        }

        buttonRightText.setOnClickListener {
            listener!!.onButtonRightTextClick()
        }

    }

    private fun updateView(typeArray: TypedArray) {
        setMessageTitle(typeArray.getString(PokemonBaseToolbar_android_text))
        showBackButton(typeArray.getBoolean(PokemonBaseToolbar_showBackButton, true))
        setIconBackButton(typeArray.getBoolean(PokemonBaseToolbar_showCloseButton, false))
        setIconRightButton(typeArray.getBoolean(PokemonBaseToolbar_showRightButton, false))
        setTextRightButton(typeArray.getString(PokemonBaseToolbar_textRightButton))
        showBottomBarLine(typeArray.getBoolean(PokemonBaseToolbar_showBottomBarLine, true))
        setIconLeft(typeArray.getDrawable(PokemonBaseToolbar_iconLeftButton))
        setIconRight(typeArray.getDrawable(PokemonBaseToolbar_iconRightButton))
        typeArray.recycle()
    }

    private fun setIconRight(drawable: Drawable?) {
        drawable?.let {
            drawableRight.setImageDrawable(it)
        }
    }

    private fun setIconLeft(drawable: Drawable?) {
        drawable?.let {
            drawableLeft.setImageDrawable(it)
        }
    }

    fun setMessageTitle(message: String?) {
        textViewErrorMessage.text = message
    }

    fun showBackButton(show: Boolean) {
        buttonBack.visibility = if (show)
            View.VISIBLE
        else
            View.GONE
    }

    fun setIconRightButton(show: Boolean) {
        buttonRight.visibility = if (show)
            View.VISIBLE
        else
            View.GONE
    }

    fun setListener(listener: ToolbarListener) {
        this.listener = listener

    }

    fun setTextRightButton(message: String?) {
        buttonRightText.text = message
    }

    fun setIconBackButton(isCloseBtn: Boolean) {
        this.isCloseBtn = isCloseBtn
        if (isCloseBtn) {
            this.buttonBack.setImageResource(R.drawable.ic_close)
        } else {
            this.buttonBack.setImageResource(R.drawable.arrow_back)
        }
    }

    fun setOnClickListenerRightButton(callback: (it: View) -> Unit) {
        buttonRightText.setOnClickListener {
            callback(it)
        }
        drawableLeft.setOnClickListener {
            callback(it)
        }
        drawableRight.setOnClickListener {
            callback(it)
        }
        buttonRight.setOnClickListener {
            callback(it)
        }
    }

    fun setIconRightButtonImage(resourceId: Int) {
        this.drawableRight.setImageResource(resourceId)
    }

    fun setBackButtonColor(colorResource: Int) {
        buttonBack.setColorFilter(ContextCompat.getColor(context, colorResource))
    }

    fun setTitleTextColor(colorResource: Int) {
        textViewErrorMessage.setTextColor(ContextCompat.getColor(context, colorResource))
    }

    fun setRightButtonColor(colorResource: Int) {
        drawableRight.setColorFilter(ContextCompat.getColor(context, colorResource))
    }

    fun setTitleTextSize(textSize: Float) {
        textViewErrorMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
    }

    fun showBottomBarLine(show: Boolean) {
        bottomLine.visibility = if (show)
            View.VISIBLE
        else
            View.INVISIBLE
    }

    interface ToolbarListener {
        fun onBackPressed()
        fun onFinishScreen()
        fun onButtonRightTextClick()
    }

}