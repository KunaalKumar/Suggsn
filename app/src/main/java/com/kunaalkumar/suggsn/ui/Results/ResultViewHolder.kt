package com.kunaalkumar.suggsn.ui.Results

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.suggsn.taste_dive.TDItem
import kotlinx.android.synthetic.main.result_list_item.view.*

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG: String = "ResultViewHolder"

    fun bindView(data: TDItem) {
        itemView.result_name.text = data.Name
        itemView.result_description.text = data.wTeaser
    }
}