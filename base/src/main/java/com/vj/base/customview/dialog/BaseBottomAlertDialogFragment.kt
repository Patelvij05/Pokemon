package com.vj.base.customview.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.squareup.picasso.Picasso
import com.vj.base.R
import com.vj.base.customview.adapter.BottomAlertDialogButtonsAdapter
import com.vj.base.customview.adapter.BottomAlertDialogLinksAdapter
import com.vj.base.utils.ToolUtil
import kotlinx.android.synthetic.main.fragment_base_bottom_alert_dialog.*

class BaseBottomAlertDialogFragment: DialogFragment() {

    var image: String? = null
    var title: BottomAlertDialogContent? = null
    var subTitle: BottomAlertDialogContent? = null
    var link: BottomAlertDialogContent? = null
    var linkList: List<BottomAlertDialogContent>? = null
    var buttons: List<BottomAlertDialogContent>? = null

    override fun onStart() {
        super.onStart()
        setupDialogContents()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT

            dialog.window?.apply {

                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(width, height)
                setWindowAnimations(R.style.DialogPresentStyle)

                val windowlp = attributes
                windowlp.gravity = Gravity.BOTTOM
                attributes = windowlp
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_bottom_alert_dialog, container, false)
    }

    private fun onButtonClicked(index: Int){
        buttons?.let{
            val button = it[index]
            button.onClickAction?.invoke()
        }
    }

    private fun onLinkClicked(index: Int) {
        linkList?.let {
            val link = it[index]
            link.onClickAction?.invoke()
        }
    }

    private fun setupDialogContents() {
        image?.let {
            if(!it.equals("")) {
                Picasso.with(context).load(it).into(iconImageView)

                YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(5)
                    .playOn(iconImageView)
            } else {
                iconImageView.setImageResource(R.drawable.ic_info)
            }

        }?:
        run {
            iconImageView.visibility = View.GONE
        }

        title?.let {
            titleTextView.text = it.text
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX
                    , resources.getDimension(it.fontSizeDimen))
            titleTextView.setTextColor(resources.getColor(it.fontColor, null))

            if(it.fontTypeface != null) {
                titleTextView.typeface = it.fontTypeface
            }

            if(it.fontTypeFaceStyle != null){
                titleTextView.setTypeface(titleTextView.typeface, it.fontTypeFaceStyle)
            } else {
                titleTextView.setTypeface(titleTextView.typeface, Typeface.BOLD)
            }
        }?:
        run {
            titleTextView.visibility = View.GONE
        }

        subTitle?.let {

            if(ToolUtil.isTextContainsHtml(it.text!!)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    subTitleTextView.text = Html.fromHtml(it.text, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    subTitleTextView.text = Html.fromHtml(it.text)
                }
            }else{
                subTitleTextView.text = it.text
            }

            subTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX
                    , resources.getDimension(it.fontSizeDimen))
            subTitleTextView.setTextColor(resources.getColor(it.fontColor, null))

            subTitleTextView.setOnClickListener {
                subTitle?.onClickAction?.invoke()
            }

            if(it.fontTypeface != null) {
                subTitleTextView.typeface = it.fontTypeface
            }
        }?:
        run {
            subTitleTextView.visibility = View.GONE
        }

        link?.let {
            val s = Html.fromHtml(it.text) as Spannable
            for (u in s.getSpans(0, s.length, URLSpan::class.java)) {
                s.setSpan(object : UnderlineSpan() {
                    override fun updateDrawState(tp: TextPaint) {
                        tp.isUnderlineText = false
                        tp.color = resources.getColor(it.fontColor, null)
                    }
                }, s.getSpanStart(u), s.getSpanEnd(u), 0)
            }
            linkTextView.text = s
            linkTextView.isAllCaps = it.isAllCap
            linkTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX
                    , resources.getDimension(it.fontSizeDimen))
            linkTextView.movementMethod = LinkMovementMethod.getInstance()

            if(it.fontTypeface != null) {
                linkTextView.typeface = it.fontTypeface
            }
        }?:
        run {
            linkTextView.visibility = View.GONE
        }

        linkList?.let {links ->
            val adapter = BottomAlertDialogLinksAdapter(activity!!, links)
            adapter.onItemClicked = {
                onLinkClicked(it)
            }
            baseLinkTextRecyclerView.layoutManager = LinearLayoutManager(activity)
            baseLinkTextRecyclerView.adapter = adapter
        } ?:
        run {
            baseLinkTextRecyclerView.visibility = View.GONE
        }

        buttons?.let { buttons ->

            val adapter = BottomAlertDialogButtonsAdapter(activity!!, buttons)
            adapter.onItemClicked = {
                onButtonClicked(it)
            }

            baseBottomAlertDialogRecyclerView.layoutManager = LinearLayoutManager(activity!!)
            baseBottomAlertDialogRecyclerView.adapter = adapter

        } ?:
        run {
            baseBottomAlertDialogRecyclerView.visibility = View.GONE
        }
    }
}
