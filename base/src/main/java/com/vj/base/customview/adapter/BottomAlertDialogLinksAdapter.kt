package com.vj.base.customview.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vj.base.R
import com.vj.base.customview.dialog.BottomAlertDialogContent
import kotlinx.android.synthetic.main.item_bottom_alert_dialog_link_cell.view.*

class LinkViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    val linkItem = item

    fun bindView(linkContent: BottomAlertDialogContent, onItemClicked: ((Int) -> Unit), context: Context) {
        linkItem.link.text = linkContent.text
        linkItem.link.setTextColor(context.resources.getColor(linkContent.fontColor, null))
        linkItem.link.isAllCaps = linkContent.isAllCap
        linkItem.link.setTextSize(TypedValue.COMPLEX_UNIT_PX
                , context.resources.getDimension(linkContent.fontSizeDimen))

        if (linkContent.fontTypeface != null) {
            linkItem.link.typeface = linkContent.fontTypeface
        }
        linkItem.setOnClickListener {
            onItemClicked.invoke(adapterPosition)
        }
    }
}

class BottomAlertDialogLinksAdapter(private val context: Context,
                                    private var links: List<BottomAlertDialogContent>) : RecyclerView.Adapter<LinkViewHolder>() {

    lateinit var onItemClicked: ((Int) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bottom_alert_dialog_link_cell, parent, false)
        return LinkViewHolder(view)
    }

    override fun getItemCount(): Int {
        return links.size
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        holder.bindView(links[position], onItemClicked, context)
    }
}