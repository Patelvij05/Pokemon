package com.vj.base.customview.progressbar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.vj.base.R
import kotlinx.android.synthetic.main.bottom_progressbar.view.*

class BaseProgressBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
        inflate(context, R.layout.bottom_progressbar, this)
        baseProgressBarTextView.setTextColor(ContextCompat.getColor(context,R.color.progress_text_color))
        baseProgressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(context,R.color.progress_color))
        baseProgressBar.progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context,R.color.progress_background_color))
    }

    fun setTextSize(fontSize : Float){
        baseProgressBarTextView.textSize = fontSize
    }

    fun setTextColor(color : Int) {
        baseProgressBarTextView.setTextColor(ContextCompat.getColor(context,
                color))
    }

    fun setProgressColor(color : Int) {
        baseProgressBar.progressTintList = ColorStateList.valueOf(color)
    }

    fun setProgressBackgroundColor(color : Int){
        baseProgressBar.progressBackgroundTintList = ColorStateList.valueOf(color)
    }

    fun updateView(model : BaseProgressBarModel){
        baseProgressBarTextView.text = String.format("%d/%d %s", model.progressValue, model.progressTotal, model.textTitle)
        baseProgressBar.max = model.progressTotal
        baseProgressBar.setProgress(model.progressValue)
    }
}

data class BaseProgressBarModel(
        var textTitle: String,
        var progressValue: Int,
        var progressTotal: Int
)