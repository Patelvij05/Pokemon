package com.vj.base.customview.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vj.base.R
import com.vj.base.customview.dialog.BottomAlertDialogContent
import kotlinx.android.synthetic.main.item_bottom_alert_dialog_button_cell.view.*

class BottomAlertDialogButtonsAdapter(private val context: Context,
                                      private var items: List<BottomAlertDialogContent>
                                     ): RecyclerView.Adapter<BottomAlertDialogButtonsAdapter.ButtonViewHolder>() {
    lateinit var onItemClicked: ((Int) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bottom_alert_dialog_button_cell, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ButtonViewHolder, position: Int) {
        items?.let {
            val buttonContent = it[position]
            viewHolder.button.text = buttonContent.text
            if(it[position].fontTypeface == Typeface.DEFAULT_BOLD)
                viewHolder.button.setTypeface(viewHolder.button.typeface,Typeface.BOLD)

            viewHolder.button.setTextSize(TypedValue.COMPLEX_UNIT_PX
                    , context.resources.getDimension(buttonContent.fontSizeDimen))
            viewHolder.button.setTextColor(context.resources.getColor(buttonContent.fontColor, null))
            viewHolder.button.contentDescription = "bottomAlertDialogButton_$position"

            try {
                buttonContent?.bgResource?.let {
                    viewHolder.button.setBackgroundResource(it)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ButtonViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val button = itemView.bottomAlertDialogButton

        init {
            button.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }
    }
}