package com.vj.base.customview.dialog


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import com.vj.base.R
import kotlinx.android.synthetic.main.fragment_base_alert_dialog.*


private const val ARGS_IMAGE = "dialog_image"
private const val ARGS_TITLE = "dialog_title"
private const val ARGS_CONTENT = "dialog_content"
private const val ARGS_TITLE_BUTTON = "dialog_button"

class BaseAlertDialogFragment : DialogFragment() {

    @DrawableRes
    private var image: Int? = null

    private var title: String? = null
    private var content: String? = null
    private var titleButton: String? = null
    private var onPressButtonOk: (() -> Unit)? = null

    private var dialogClickListener: DialogOnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            arguments?.let(this::onInitInstance)
        } else {
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_alert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogClickListener = if (parentFragment != null) {
            parentFragment as DialogOnClickListener
        } else {
            activity as DialogOnClickListener
        }

        image?.let {
            if ( it != 0 ) {
                iconImageView.setImageResource(it)
            } else {
                iconImageView.visibility = View.GONE
            }
        } ?:
        run {
            iconImageView.visibility = View.GONE
        }

        title?.let { titleTextView.text = title } ?: run {
            titleTextView.visibility = View.GONE
        }

        content?.let { contentTextView.text = content } ?: run {
            contentTextView.visibility = View.GONE
        }

        titleButton?.let {
            acceptButton.text = titleButton
            acceptButton.setOnClickListener {
                dismiss()
                onPressButtonOk?.invoke()
                dialogClickListener?.onDialogAcceptClickListener(it)
            }
        } ?: run {
            acceptButton.visibility = View.GONE
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            image?.let { putInt(ARGS_IMAGE, it) }
            putString(ARGS_TITLE, title)
            putString(ARGS_CONTENT, content)
            putString(ARGS_TITLE_BUTTON, titleButton)
        }
    }


    private fun onInitInstance(arguments: Bundle) {
        arguments.let {
            image = it.getInt(ARGS_IMAGE)
            title = it.getString(ARGS_TITLE)
            content = it.getString(ARGS_CONTENT)
            titleButton = it.getString(ARGS_TITLE_BUTTON)
        }

    }

    private fun onRestoreInstanceState(bundle: Bundle?) {
        bundle?.let {
            image = it.getInt(ARGS_IMAGE)
            title = it.getString(ARGS_TITLE)
            content = it.getString(ARGS_CONTENT)
            titleButton = it.getString(ARGS_TITLE_BUTTON)
        }
    }

    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.WRAP_CONTENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT

        dialog?.window?.apply {
            setBackgroundDrawable(
                    ColorDrawable(
                            resources.getColor(
                                    android.R.color.transparent,
                                    context!!.theme
                            )
                    )
            )
            setLayout(width, height)
            setWindowAnimations(R.style.AlertDialogAnimation)
        }

    }


    interface DialogOnClickListener {
        fun onDialogAcceptClickListener(v: View)
    }


    companion object {
        fun newInstance(image: Int?, title: String?, content: String?, titleButton: String?, onPressButtonOk: (() -> Unit)? = null) =
                BaseAlertDialogFragment().apply {
                    arguments = Bundle().apply {
                        image?.let { putInt(ARGS_IMAGE, it) }
                        putString(ARGS_TITLE, title)
                        putString(ARGS_CONTENT, content)
                        putString(ARGS_TITLE_BUTTON, titleButton)
                    }
                    this.onPressButtonOk = onPressButtonOk
                }
    }


    class Builder {
        private var image: Int? = null
        private var title: String? = null
        private var content: String? = null
        private var titleButton: String? = null
        private var onPressButtonOk: (() -> Unit)? = null

        fun image(@DrawableRes image: Int) = apply { this.image = image }
        fun title(title: String) = apply { this.title = title }
        fun content(content: String) = apply { this.content = content }
        fun titleButton(titleButton: String) = apply { this.titleButton = titleButton }
        fun onPressButtonOk(onPressButtonOk: (() -> Unit)? = null) = apply { this.onPressButtonOk = onPressButtonOk }

        fun build() = newInstance(
                image = image,
                title = title,
                content = content,
                titleButton = titleButton,
                onPressButtonOk = onPressButtonOk
        )
    }

}

